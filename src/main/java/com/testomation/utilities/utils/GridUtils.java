package com.testomation.utilities.utils;

import com.testomation.constatnts.GlobalConstants;
import com.testomation.utilities.generic.Generic;
import com.testomation.utilities.report.Log;

import java.io.File;
import java.lang.management.ManagementFactory;

public class GridUtils {

    public void start_hub_node(){
        String hubStatus=null;


        try {
            hubStatus = APIUtils.callRestApi("http://localhost:4444/grid/api/hub");
            if(!hubStatus.contains("no resp")){
                GlobalConstants.HUB_STATUS=true;
            }
            if(hubStatus.contains("\"total\":20")){
                GlobalConstants.NODE_STATUS=true;
            }

        }catch (Exception e){
            System.out.println("ERROR");
            Log.info(e.getMessage());
            e.printStackTrace();
        }

        if(GlobalConstants.NODE_STATUS){

        } else {
            if(!GlobalConstants.HUB_STATUS){
                startHub();
            }
            startNode();
        }
    }

    public void startHub(){
        if(GlobalConstants.HUB_STATUS){

        } else {
            String hubStatus = null;
            Thread object = new Thread(new runHub());
            object.start();
            try {
                Thread.sleep(10000);
                hubStatus = APIUtils.callRestApi("http://localhost:4444/grid/api/hub");
                if (hubStatus != null) {
                    GlobalConstants.HUB_STATUS = true;

                } else {
                    Thread.sleep(5000);
                    hubStatus = APIUtils.callRestApi("http://localhost:4444/grid/api/hub");
                    if (hubStatus != null) {
                        GlobalConstants.HUB_STATUS = true;

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    class runHub implements Runnable{
        @Override
        public void run() {
            try{

                File dir = new File(Generic.readConfigProp("seleniumGridPath"));
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start","HUB_Start.bat");
                pb.directory(dir);
                Process p = pb.start();
                String name = ManagementFactory.getRuntimeMXBean().getName();

            }
            catch (Exception e) {
                // Throwing an exception
                e.printStackTrace();
            }
        }
    }

    public void startNode(){
        String nodesStatus=null;
        Thread object = new Thread(new runNodes());
        object.start();
        try {
            Thread.sleep(10000);
            nodesStatus = APIUtils.callRestApi("http://localhost:4444/grid/api/hub");

            if (nodesStatus.contains("\"total\":20")) {
                GlobalConstants.NODE_STATUS = true;

            } else{
                Thread.sleep(5000);
                nodesStatus = APIUtils.callRestApi("http://localhost:4444/grid/api/hub");
                if (nodesStatus.contains("\"total\":20")) {
                    GlobalConstants.NODE_STATUS = true;

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    class runNodes implements Runnable{
        @Override
        public void run() {
            try{
                File dir = new File(Generic.readConfigProp("seleniumGridPath"));
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start","Nodes_Start.bat");
                pb.directory(dir);
                Process p = pb.start();
                String name = ManagementFactory.getRuntimeMXBean().getName();

            }
            catch (Exception e) {
                // Throwing an exception
                e.printStackTrace();
            }
        }
    }
}
