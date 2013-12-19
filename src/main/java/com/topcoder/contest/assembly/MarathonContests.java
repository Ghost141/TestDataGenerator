/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.contest.assembly;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0
 */
@Component
public class MarathonContests extends BaseGenerator{
    @Value("${ongoing.base.path}")
    private String fileBasePath;

    @Value("${informixoltp.contest}")
    private String informixoltp_contest;

    @Value("${informixoltp.round_segment}")
    private String round_segment;

    @Value("${informixoltp.round}")
    private String informixoltp_round;

    @Value("${dw.round}")
    private String dw_round;

    @Value("${dw.contest}")
    private String dw_contest;

    @Value("${dw.long_comp_result}")
    private String long_comp_result;

    @Value("${dw.coder}")
    private String dw_coder;

    private Integer roundId = 2001;

    private Integer contestId = 2001;

    private static final String contestFolder = "Topcoder NodeJS Marathon Contests API Queries";

    private static final String fileName = "test_data.sql";

    private static final List<Integer> ROUND_TYPE_ID = ImmutableList.of(
            1, 2, 3, 4, 10, 6, 11, 7, 8, 9, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 27
    );

    private static final List<Integer> CODER_ID = ImmutableList.of(
            2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018,
            2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2033, 2034, 2035, 2036,
            2037, 2038, 2039, 2040, 2041, 2042, 2043, 2044, 2045, 2046, 2047, 2048, 2049, 2050
    );

    private static final Random r = new Random(System.currentTimeMillis());

    public void generate() throws Exception{

//        generateActiveContest();

        write("------------------------------------------------------------------------------------------------------------------------------");

        generatePastContest();
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateActiveContest() throws IOException {
        write("database informixoltp;");
        write();
        for (int i = 0; i < 5; i++) {
            write(informixoltp_contest,
                    ImmutableMap.<String, String>builder()
                            .put("cid", String.valueOf(contestId++))
                            .put("name", "test contest " + (contestId - 1))
                            .build()
            );
            write();

            for (int j = 0; j < 20; j++) {
                DateTime startTime = new DateTime().minusDays(Math.abs(r.nextInt() % 50));
                write(
                        informixoltp_round,
                        ImmutableMap.<String, String>builder()
                                .put("rid", String.valueOf(roundId++))
                                .put("cid", String.valueOf(contestId - 1))
                                .put("name", "test round " + (roundId - 1))
                                .put("rtid", String.valueOf(ROUND_TYPE_ID.get(r.nextInt(ROUND_TYPE_ID.size()))))
                                .build()
                );
                write(
                        round_segment,
                        ImmutableMap.<String, String>builder()
                                .put("rid", String.valueOf(roundId - 1))
                                .put("start", DATE_TIME_FORMAT.format(startTime.toDate()))
                                .put("end", DATE_TIME_FORMAT.format(startTime.plusDays(30).toDate()))
                                .put("status", "A")
                                .build()
                );
                write();
            }
        }
    }

    private void generatePastContest() throws IOException {
        write("database topcoder_dw;");
        write();

        Integer cid = 2001;
        Integer rid = 2001;
        for (int i = 0; i < 10; i++) {
            DateTime startDate = new DateTime().plusDays(r.nextInt(200));

            Integer sindex = r.nextInt(200);

            write(
                    dw_contest,
                    ImmutableMap.<String, String>builder()
                            .put("cid", String.valueOf(cid++))
                            .put("name", "test contest " + (cid - 1))
                            .put("start", "CURRENT + " + sindex + " UNITS DAY")
                            .put("end", "CURRENT + " + (sindex + 30) + " UNITS DAY")
//                            .put("start", DATE_TIME_FORMAT.format(startDate.toDate()))
//                            .put("end", DATE_TIME_FORMAT.format(startDate.plusDays(30).toDate()))
                    .build()
            );

            for (int j = 0; j < 20; j++) {
                write(
                        dw_round,
                        ImmutableMap.<String, String>builder()
                                .put("rid", String.valueOf(rid++))
                                .put("cid", String.valueOf(cid - 1))
                                .put("name", "test round " + (rid - 1))
                                .put("short_name", "test round " + (rid - 1))
                                .put("calid", "4017")
                                .put("rtid", String.valueOf(ROUND_TYPE_ID.get(r.nextInt(ROUND_TYPE_ID.size()))))
                                .build()
                );

                write(
                        long_comp_result,
                        ImmutableMap.<String, String>builder()
                                .put("rid", String.valueOf(rid - 1))
                                .put("coder", String.valueOf(CODER_ID.get(r.nextInt(50))))
                                .put("place", "1")
                                .put("point", String.valueOf(r.nextDouble() * 1000000))
                                .build()
                );
                write();
            }
            write();
        }
    }

    public MarathonContests() {
        super();
    }

//    @PostConstruct
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
