package com.checker.services;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

@Component
public class TaskCheckService {

    private static final String FAILED_PATTERN = "(.*)failed(.*)";
    private static final String ZERO = "0";
    private static final String BASH = "bash ";
    private static final String SLASH = "/";
    private static final String USER_DIRECTORY = "/home/teacher/TaskChecker/";
    private static final String CHECK = "check";
    private static final String SH = ".sh ";

    public boolean getGradeFromScriptResult(List<String> scriptOutput) {
        if (scriptOutput.isEmpty()) {
            return false;
        }
        for (String s : scriptOutput) {
            if (s.matches(FAILED_PATTERN)) {
                return false;
            }
        }
        return true;
    }

    public List<String> initTestScript(String ip, int taskNumber, String discipline) {
        List<String> scriptOutput = new LinkedList<>();
        String s;
        String strLabNumber = String.valueOf(taskNumber);
        if (taskNumber < 10) {
            strLabNumber = ZERO + strLabNumber;
        }

        try {
            Process process = Runtime.getRuntime().exec(BASH + USER_DIRECTORY + discipline + SLASH + CHECK + strLabNumber + SH + ip);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = br.readLine()) != null) {
                if (!s.equals("\n")) {
                    scriptOutput.add(s);
                }
            }
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }

        return scriptOutput;
    }

    public static String generateOutputMessage(List<String> message) {
        StringBuilder str = new StringBuilder();
        for (String string : message) {
            str.append(string).append("\n");
        }
        return str.toString();
    }
}