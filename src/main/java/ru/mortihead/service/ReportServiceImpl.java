package ru.mortihead.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mortihead.model.CarEntity;
import ru.mortihead.model.CityEntity;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportServiceImpl implements ReportService {

    private static JasperPrint jasperPrint;
    private final CarService carsService;
    private final CityService cityService;

    @Override
    public ResponseEntity<Resource> downloadPdf(Integer brandId) throws Exception {

        Session session = createSession();
        Transaction transaction = session.beginTransaction();

        Map parameters = getParameters(session);
        JRDataSource dataSource = getDataSource(brandId);
        URL reportTemplate = getClass().getClassLoader().getResource("report_templates/report1.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate.getPath());
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //JasperExportManager.exportReportToPdfFile(jasperPrint, "c:/temp/report1.pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        outputStream.flush();

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        transaction.rollback();
        session.close();

        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.APPLICATION_PDF)
                .body(byteArrayResource);


    }

    /**
     * Существует несколько способов реализации отчета с несколькими источниками данных (т.е. когда в одном отчете
     * находятся сразу несколько отчетов, не обязательно связанные с мастер-отчетом)
     * Этот способ - через создание SubReports и передачу из Datasources и самих Subreports через параметры
     * главного отчета
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<Resource> downloadMultipleDataSourcesReportPdf() throws Exception {
        JasperReport jasperMasterReport = JasperCompileManager.compileReport(getClass().getClassLoader().getResource(
                "report_templates/report_multiple_datasources.jrxml").getPath());
        JasperReport jasperSubReportCars = JasperCompileManager.compileReport(getClass().getClassLoader().getResource(
                "report_templates/subreport_cars.jrxml").getPath());
        JasperReport jasperSubReportCities = JasperCompileManager.compileReport(getClass().getClassLoader().getResource(
                "report_templates/subreport_cities.jrxml").getPath());

        Map<String, Object> parameters = new HashMap();
        // Эти параметры заранее определены в Мастер-отчете и установлены в соответствующие компоненты
        parameters.put("SubReportCars", jasperSubReportCars);
        parameters.put("SubReportCarsDatasource", getCarsDataSource());
        parameters.put("SubReportCities", jasperSubReportCities);
        parameters.put("SubReportCitiesDatasource", getCitiesDataSource());

        // Фиктивный Datasource c One Empty Record - чтобы отчет не выводил No Data
        jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, new JREmptyDataSource(1));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        outputStream.flush();

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.APPLICATION_PDF)
                .body(byteArrayResource);
    }

    private JRDataSource getDataSource(Integer brandId) {
        List<CarEntity> cars = carsService.findByBrandId(brandId);
        return new JRBeanCollectionDataSource(cars);
    }

    private JRDataSource getCarsDataSource() {
        List<CarEntity> cars = carsService.findAll();
        return new JRBeanCollectionDataSource(cars);
    }

    private JRDataSource getCitiesDataSource() {
        List<CityEntity> cities = cityService.findAll();
        return new JRBeanCollectionDataSource(cities);
    }


    private static Map getParameters(Session session) {
        Map parameters = new HashMap();
        parameters.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION, session);
        parameters.put("ReportTitle", "Address Report");
        List cityFilter = new ArrayList(3);
        cityFilter.add("Ufa");
        parameters.put("CityFilter", cityFilter);
        parameters.put("OrderClause", "city");
        return parameters;
    }

    private static Session createSession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        return sessionFactory.openSession();
    }
}
