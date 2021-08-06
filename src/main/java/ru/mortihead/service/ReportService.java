package ru.mortihead.service;

import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ReportService {
    ResponseEntity<Resource> downloadPdf(Integer brandId) throws JRException, Exception;

    ResponseEntity<Resource> downloadMultipleDataSourcesReportPdf() throws Exception;
}
