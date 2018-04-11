package com.ohair.stephen.aws_nexrad_java;

import java.io.PrintWriter;

import ucar.nc2.NCdumpW;
import ucar.nc2.constants.FeatureType;
import ucar.nc2.dt.RadialDatasetSweep;
import ucar.nc2.dt.TypedDatasetFactory;
import ucar.nc2.util.CancelTask;

/**
 * Utility class to test data parsing functionality.
 * 
 * Adapted from :
 * https://www1.ncdc.noaa.gov/pub/data/radar/Radar-Decoding-JavaSolution.txt
 *
 */
public class App {

    /**
     * Takes a given NetCDF file and parses it.
     * 
     * @param fileIn
     */
    public static void parse(String fileIn) {

        try (PrintWriter output = new PrintWriter(System.out)) {

            // print an 'ncdump' of the file
            // NCdump.print(fileIn, System.out);
            NCdumpW.print(fileIn, output);
            // NCdumpW.print("KTLX19910705_235109", output);

            CancelTask emptyCancelTask = new CancelTask() {
                @Override
                public boolean isCancel() {
                    return false;
                }

                @Override
                public void setError(String arg0) {
                }

                @Override
                public void setProgress(String msg, int progress) {
                }
            };

            // open the file and represent as a
            // RadialDatasetSweep object
            RadialDatasetSweep rds = (RadialDatasetSweep) TypedDatasetFactory.open(FeatureType.RADIAL, fileIn,
                    emptyCancelTask, new StringBuilder());

            // radar information
            String stationID = rds.getRadarID();
            String stationName = rds.getRadarName();
            boolean isVolume = rds.isVolume();

            System.out.println("stationID = " + stationID);
            System.out.println("stationName = " + stationName);
            System.out.println("isVolume = " + isVolume);
            System.out.println("station location = " + rds.getCommonOrigin());

            // Read a radial variable
            RadialDatasetSweep.RadialVariable varRef = (RadialDatasetSweep.RadialVariable) rds
                    .getDataVariable("Reflectivity");

            // Read a single sweep
            int sweepNum = 0;
            RadialDatasetSweep.Sweep sweep = varRef.getSweep(sweepNum);

            float meanElev = sweep.getMeanElevation();
            int nrays = sweep.getRadialNumber();
            float beamWidth = sweep.getBeamWidth();
            int ngates = sweep.getGateNumber();
            float gateSize = sweep.getGateSize();

            System.out.println("meanElev = " + meanElev);
            System.out.println("nrays = " + nrays);
            System.out.println("beamWidth = " + beamWidth);
            System.out.println("ngates = " + ngates);
            System.out.println("gateSize = " + gateSize);

            // Read data variable at radial level -
            // this is where actual data is read
            // into memory
            for (int i = 0; i < nrays; i++) {
                float azimuth = sweep.getAzimuth(i);
                float elevation = sweep.getElevation(i);
                float[] data = sweep.readData(i);
                // data.length should equal ngates

            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
