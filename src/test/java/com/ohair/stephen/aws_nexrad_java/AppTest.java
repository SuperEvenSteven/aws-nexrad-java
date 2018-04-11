package com.ohair.stephen.aws_nexrad_java;

import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Unit tests for NexRad Level II samples.
 * 
 * Sample source : https://noaa-nexrad-level2.s3.amazonaws.com/2001/10/10/KABR/KAKQ20010101_080138.gz
 * 
 * @author Stephen O'Hair
 *
 */
public class AppTest {

    private static Logger log = Logger.getLogger(AppTest.class);

    @Test
    public void TestParseParsesGivenValidNexradSample() throws URISyntaxException {
        log.info("running test");
        URL url = this.getClass().getClassLoader().getResource("volume-scan-sample/KTLX19910705_235109");
        App.parse(url.toURI().getPath());
        log.info("test complete");
    }
}
