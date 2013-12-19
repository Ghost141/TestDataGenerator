/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.contest.assembly;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0
 */
@Component
public class StudioTopRanked extends BaseGenerator {
    @Value("${ongoing.base.path}")
    private String fileBasePath;

    private static final String contestFolder = "Topcoder NodeJS Software Rating Distribution and Rating History API";

    private static final String fileName = "studio_top_rank.sql";
//    private static final String fileName = "studio_review_opportunities.sql";

    private List<Integer> userIds = ImmutableList.of(132456, 132457, 132458, 124764, 124772, 124766, 124776, 124834, 124835, 124836, 124852, 124856, 124857, 124861, 124916);
//    private List<Integer> userIds = ImmutableList.of(132456);

    private List<Integer> wins = ImmutableList.of(5,4,3,2,1);

    @Override
    protected void generate() throws Exception {
        write(TCS_CATALOG).newLine();

        generateTopRank();
//        generateReviewOpportunities();
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateReviewOpportunities() throws IOException {
        Integer project_id = 2001;
        Integer project_phase_id = 2001;
        Integer resource_id = 2001;

        // screen
        for (int i = 0; i < 5; i++) {
            write(tcs_catalog_project
                    , ImmutableMap.<String, String>builder()
                    .put("pid", String.valueOf(project_id))
                    .put("pcid", String.valueOf(STUDIO_TYPE_ID.get(i)))
                    .put("psid", "1")
                    .build()
            ).write(tcs_catalog_project_info
                    , ImmutableMap.<String, String>builder()
                    .put("pid", String.valueOf(project_id))
                    .put("type", "6")
                    .put("value", "test project " + project_id)
                    .build()
            ).write("INSERT INTO project_phase(project_phase_id, project_id, phase_type_id, phase_status_id, scheduled_start_time, actual_start_time, scheduled_end_time, duration, create_user, create_date, modify_user, modify_date) VALUES(@ppid@, @pid@, @phase_type_id@, @phase_status_id@, @start@, @actual_start@, @end@, @duration@, 132456, CURRENT, 132456, CURRENT);"
                    , ImmutableMap.<String, String>builder()
                    .put("ppid", String.valueOf(project_phase_id++))
                    .put("pid", String.valueOf(project_id))
                    .put("phase_type_id", "1")
                    .put("phase_status_id", "2")
                    .put("start", "CURRENT - 3 UNITS DAY")
                    .put("actual_start", "CURRENT - 3 UNITS DAY")
                    .put("end", "CURRENT - 1 UNITS DAY")
                    .put("duration", "172800000")
                    .build()
            ).write(tcs_catalog_project_phase
                    , ImmutableMap.<String, String>builder()
                    .put("ppid", String.valueOf(project_phase_id++))
                    .put("pid", String.valueOf(project_id))
                    .put("phase_type_id", "2")
                    .put("phase_status_id", "2")
                    .put("start", "CURRENT - 3 UNITS DAY")
                    .put("end", "CURRENT + 1 UNITS DAY")
                    .put("duration", "345600000")
                    .build()
            ).write(tcs_catalog_project_phase
                    , ImmutableMap.<String, String>builder()
                    .put("ppid", String.valueOf(project_phase_id++))
                    .put("pid", String.valueOf(project_id))
                    .put("phase_type_id", "3")
                    .put("phase_status_id", "2")
                    .put("start", "CURRENT + 1 UNITS DAY")
                    .put("end", "CURRENT + 2 UNITS DAY")
                    .put("duration", "86400000")
                    .build()
            ).write(tcs_catalog_project_phase
                    , ImmutableMap.<String, String>builder()
                    .put("ppid", String.valueOf(project_phase_id++))
                    .put("pid", String.valueOf(project_id))
                    .put("phase_type_id", "16")
                    .put("phase_status_id", "2")
                    .put("start", "CURRENT - 1 UNITS DAY")
                    .put("end", "CURRENT")
                    .put("duration", "86400000")
                    .build()
            ).write(tcs_catalog_resource
                    , ImmutableMap.<String, String>builder()
                    .put("rid", String.valueOf(resource_id))
                    .put("rrid", r.nextBoolean() ? "2" : "19")
                    .put("pid", String.valueOf(project_id))
                    .build()
            ).write(tcs_catalog_resource_info
                    , ImmutableMap.<String, String>builder()
                    .put("rid", String.valueOf(resource_id))
                    .put("type", "1")
                    .put("value", String.valueOf(userIds.get(r.nextInt(userIds.size()))))
                    .build()
            ).newLine();
            project_id++;
            resource_id++;
        }

        // spec
        for (int i = 0; i < 5; i++) {
            write(tcs_catalog_project
                    , ImmutableMap.<String, String>builder()
                    .put("pid", String.valueOf(project_id))
                    .put("pcid", String.valueOf(STUDIO_TYPE_ID.get(i)))
                    .put("psid", "1")
                    .build()
            ).write(tcs_catalog_project_info
                    , ImmutableMap.<String, String>builder()
                    .put("pid", String.valueOf(project_id))
                    .put("type", "6")
                    .put("value", "test project " + project_id)
                    .build()
            ).write(tcs_catalog_project_phase
                    , ImmutableMap.<String, String>builder()
                    .put("ppid", String.valueOf(project_phase_id++))
                    .put("pid", String.valueOf(project_id))
                    .put("phase_type_id", "2")
                    .put("phase_status_id", "1")
                    .put("start", "CURRENT + 1 UNITS DAY")
                    .put("end", "CURRENT + 5 UNITS DAY")
                    .put("duration", "345600000")
                    .build()
            ).write(tcs_catalog_project_phase
                    , ImmutableMap.<String, String>builder()
                    .put("ppid", String.valueOf(project_phase_id++))
                    .put("pid", String.valueOf(project_id))
                    .put("phase_type_id", "14")
                    .put("phase_status_id", "2")
                    .put("start", "CURRENT")
                    .put("end", "CURRENT + 1 UNITS DAY")
                    .put("duration", "86400000")
                    .build()
            ).write(tcs_catalog_resource
                    , ImmutableMap.<String, String>builder()
                    .put("rid", String.valueOf(resource_id))
                    .put("rrid", "18")
                    .put("pid", String.valueOf(project_id))
                    .build()
            ).write(tcs_catalog_resource_info
                    , ImmutableMap.<String, String>builder()
                    .put("rid", String.valueOf(resource_id))
                    .put("type", "1")
                    .put("value", String.valueOf(userIds.get(r.nextInt(userIds.size()))))
                    .build()
            ).newLine();
            project_id++;
            resource_id++;
        }

    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateTopRank() throws IOException {
        Integer pid = 2001;
        Integer prize_id = 2001;
        Integer submission_id = 2001;
        Integer upload_id = 2001;
        Integer resource_id = 2001;

        Integer rating = 1000;
        for (Integer uid : userIds.subList(0, 5)) {
//            Integer length = r.nextInt(STUDIO_TYPE_ID.size());
//            List<Integer> pcids = Lists.newArrayList(STUDIO_TYPE_ID);
//            Collections.shuffle(pcids);
            Integer length = STUDIO_TYPE_ID.size();
            List<Integer> pcids = Lists.newArrayList(STUDIO_TYPE_ID);

            for (int i = 0; i < length; i++) {
                write(tcs_catalog_user_rating
                        , ImmutableMap.<String, String>builder()
                        .put("uid", String.valueOf(uid))
                        .put("rating", String.valueOf(rating))
                        .put("phase", String.valueOf(pcids.get(i) + 111))
                        .put("vol", String.valueOf(r.nextInt(500)))
                        .put("rating_no_vol", String.valueOf(r.nextInt(3500)))
                        .put("num", "5")
                        .build()
                );
                for (int j = 0; j < wins.get(userIds.indexOf(uid)); j++) {
                    write(tcs_catalog_project
                            , ImmutableMap.<String, String>builder()
                            .put("pid", String.valueOf(pid))
                            .put("psid", "7")
                            .put("pcid", String.valueOf(pcids.get(i)))
                            .build()
                    ).write(tcs_catalog_resource
                            , ImmutableMap.<String, String>builder()
                            .put("rid", String.valueOf(resource_id))
                            .put("rrid", "1")
                            .put("pid", String.valueOf(pid))
                            .build()
                    ).write(tcs_catalog_resource_info
                            , ImmutableMap.<String, String>builder()
                            .put("rid", String.valueOf(resource_id))
                            .put("type", "1")
                            .put("value", String.valueOf(uid))
                            .build()
                    );
                    write(tcs_catalog_prize
                            , ImmutableMap.<String, String>builder()
                            .put("prize_id", String.valueOf(prize_id))
                            .put("pid", String.valueOf(pid))
                            .put("place", "1")
                            .put("prize_amount", "2")
                            .put("ptid", "15")
                            .put("number", "10")
                            .build()
                    ).write(tcs_catalog_upload
                            , ImmutableMap.<String, String>builder()
                            .put("upid", String.valueOf(upload_id))
                            .put("pid", String.valueOf(pid))
                            .put("rid", String.valueOf(resource_id))
                            .put("utid", "1")
                            .put("usid", "1")
                            .put("parameter", "test")
                            .build()
                    ).write(tcs_catalog_submission
                            , ImmutableMap.<String, String>builder()
                            .put("sid", String.valueOf(submission_id))
                            .put("upid", String.valueOf(upload_id))
                            .put("ssid", String.valueOf(1))
                            .put("placement", "1")
                            .put("stid", "1")
                            .put("prid", String.valueOf(prize_id))
                            .build()
                    ).newLine();

                    pid++;
                    prize_id++;
                    submission_id++;
                    upload_id++;
                    resource_id++;
                }
            }
            rating += 100;
            newLine();
        }

    }

    @PostConstruct
    public void init() throws FileNotFoundException {
        File outputFile = new File(Joiner.on("/").join(fileBasePath, contestFolder, fileName));
        if (outputFile.exists()) {
            if (!outputFile.delete()) {
                throw new IllegalStateException("The file delete is failed.");
            }
        }
        setOutputFile(outputFile);
        setFos(new FileOutputStream(getOutputFile()));
    }
}
