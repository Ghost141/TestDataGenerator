/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.contest.assembly;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.google.common.base.Preconditions.*;

/**
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseGenerator {
    private File outputFile;

    private FileOutputStream fos;

    protected Random r = new Random(System.currentTimeMillis());

    protected DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected String TOPCODER_DW = "database topcoder_dw;";

    protected String TCS_CATALOG = "database tcs_catalog;";

    @Value("${dw.coder}")
    protected String dw_coder;

    @Value("${dw.language_lu}")
    protected String dw_language;

    @Value("${dw.coder_rank}")
    protected String dw_coder_rank;

    @Value("${dw.coder_rank_type}")
    protected String dw_coder_rank_type_lu;

    @Value("${dw.algo_rating_type}")
    protected String dw_algo_rating_type;

    @Value("${dw.country_coder_rank}")
    protected String dw_country_coder_rank;

    @Value("${dw.school_coder_rank}")
    protected String dw_school_coder_rank;

    @Value("${dw.school}")
    protected String dw_school;

    @Value("${dw.country}")
    protected String dw_country;

    @Value("${dw.algo_rating}")
    protected String dw_rating;

    @Value("${dw.user_achievement}")
    protected String dw_user_achievement;

    @Value("${dw.round}")
    protected String dw_round;

    @Value("${dw.round_type}")
    protected String dw_round_type;

    @Value("${dw.contest}")
    protected String dw_contest;

    @Value("${dw.long_comp_result}")
    protected String dw_long_comp_result;

    @Value("${tcs_dw.user_achievement_xref}")
    protected String tcs_dw_user_achievement_xref;

    @Value("${tcs_catalog.project}")
    protected String tcs_catalog_project;

    @Value("${tcs_catalog.user_rating}")
    protected String tcs_catalog_user_rating;

    @Value("${tcs_catalog.prize}")
    protected String tcs_catalog_prize;

    @Value("${tcs_catalog.upload}")
    protected String tcs_catalog_upload;

    @Value("${tcs_catalog.submission}")
    protected String tcs_catalog_submission;

    @Value("${tcs_catalog.resource}")
    protected String tcs_catalog_resource;

    @Value("${tcs_catalog.resource_info}")
    protected String tcs_catalog_resource_info;

    @Value("${tcs_catalog.project_phase}")
    protected String tcs_catalog_project_phase;

    @Value("${tcs_catalog.project_info}")
    protected String tcs_catalog_project_info;

    protected List<Integer> STUDIO_TYPE_ID = ImmutableList.of(16, 17, 18, 20, 21, 22, 30, 31, 32, 34);

    public BaseGenerator() {
    }

    public void execute() throws IOException {
        try {
            generate();
            flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    protected abstract void generate() throws Exception;

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }


    public void setFos(FileOutputStream fos) {
        this.fos = fos;
    }

    public BaseGenerator write(String content) throws IOException {
        try {
            fos.write(content.getBytes());
            fos.write("\n".getBytes());
            return this;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BaseGenerator write(String template, Map<String, String> property) throws IOException {
        String content = template;
        for (Map.Entry entry : property.entrySet()) {
            checkArgument(entry.getValue().toString().trim().length() != 0, "value for key - %s can't be null nor empty", entry.getKey().toString());
            content = content.replace("@" + entry.getKey() + "@", entry.getValue().toString());
        }
        checkArgument(!content.contains("@"), "content: '%s' contains invalid character.", content);
        return write(content);
    }

    public BaseGenerator write() throws IOException {
        return write("");
    }

    public BaseGenerator newLine() throws IOException {
        write();
        return this;
    }

    public void flush() throws IOException {
        fos.flush();
    }

    public void close() throws IOException {
        fos.close();
    }

}
