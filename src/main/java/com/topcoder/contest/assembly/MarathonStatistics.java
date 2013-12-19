/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.contest.assembly;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0
 */
@Component
public class MarathonStatistics extends BaseGenerator {

    @Value("${ongoing.base.path}")
    private String fileBasePath;

    private static final String contestFolder = "Topcoder NodeJS Marathon Statistics API Queries";

    private static final String fileName = "test_data.sql";

    @Override
    protected void generate() throws Exception {
        write(TOPCODER_DW).newLine();

//        generateMarathonMember();

//        generateAchievements();

//        generateTopRankCompetitor();

//        generateTopRankSchool();

        generateTopRankCountry();
    }


    @SuppressWarnings("UnusedDeclaration")
    private void generateTopRankCompetitor() throws IOException {
        r = new Random(System.currentTimeMillis());
        write(dw_language
                , ImmutableMap.<String, String>builder()
                .put("id", "1")
                .put("name", "Java")
                .put("status", "Y")
                .build()
        );
        for (int i = 0; i < 100; i++) {
            write(dw_coder
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("hn", "testUser" + i)
                    .put("lid", "1")
                    .build()
            );
        }

        List<String> userName = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            userName.add(String.valueOf(i + 3001));
        }

        Collections.shuffle(userName);

        Map<String, Integer> rr = Maps.newHashMap();

        Integer base = 0;
        for (int i = 0; i < 100; i++) {
            base += r.nextInt(40);
            rr.put(userName.get(i), base);
        }

        write(dw_algo_rating_type
                , ImmutableMap.<String, String>builder()
                .put("id", "3")
                .put("desc", "Marathon Match Rating")
                .build()
        );

        // rating
        for (int i = 0; i < 100; i++) {
            write("INSERT INTO algo_rating(coder_id, algo_rating_type_id, rating) VALUES(@id@, 3, @rating@);"
                    , ImmutableMap.<String, String>builder()
                    .put("id", String.valueOf(i + 3001))
                    .put("rating", String.valueOf(rr.get(String.valueOf(i + 3001))))
                    .build()
            );
        }


        write(dw_coder_rank_type_lu
                , ImmutableMap.<String, String>builder()
                .put("id", "2")
                .put("desc", "test coder rank type 2")
                .build()
        );

        for (int i = 0; i < 100; i++) {
            write(dw_coder_rank
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("coder_rank_type_id", "2")
                    .put("percentile", String.valueOf(r.nextDouble() * 100))
                    .put("rank", String.valueOf(100 - (userName.indexOf(String.valueOf(i + 3001)) + 1) + 1))
                    .put("alid", "3")
                    .build()
            );
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateTopRankSchool() throws IOException {
        r = new Random(System.currentTimeMillis());
        write(dw_algo_rating_type
                , ImmutableMap.<String, String>builder()
                .put("id", "3")
                .put("desc", "Marathon Match Rating")
                .build()
        );

        write(dw_coder_rank_type_lu
                , ImmutableMap.<String, String>builder()
                .put("id", "2")
                .put("desc", "test coder rank type 2")
                .build()
        );

        for (int i = 0; i < 20; i++) {
            write(dw_country
                    , ImmutableMap.<String, String>builder()
                    .put("cc", String.valueOf(i + 1))
                    .put("cn", "country" + i)
                    .build()
            );
        }

        for (int i = 0; i < 50; i++) {
            write(dw_school
                    , ImmutableMap.<String, String>builder()
                    .put("sid", String.valueOf(i + 1))
                    .put("name", "school 00" + i)
                    .put("cc", String.valueOf(r.nextInt(20) + 1))
                    .build()
            );
        }

        for (int i = 0; i < 500; i++) {
            write("INSERT INTO coder(coder_id, handle) VALUES(@cid@, '@ha@');"
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("ha", "user" + i)
                    .build()
            );
        }
        for (int i = 0; i < 500; i++) {
            write(dw_school_coder_rank
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("sid", String.valueOf(r.nextInt(50) + 1))
                    .put("coder_rank_type_id", "2")
                    .put("alid", "3")
                    .put("rank_no_tie", String.valueOf(r.nextInt(500)))
                    .put("rank", String.valueOf(i))
                    .build()
            );
        }

        for (int i = 0; i < 500; i++) {
            write("INSERT INTO algo_rating(coder_id, algo_rating_type_id, rating) VALUES(@cid@, 3, @rating@);"
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("rating", String.valueOf(r.nextInt(4000) + 1))
                    .build()
            );
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateTopRankCountry() throws IOException {
        r = new Random(System.currentTimeMillis());
        write(dw_algo_rating_type
                , ImmutableMap.<String, String>builder()
                .put("id", "3")
                .put("desc", "Marathon Match Rating")
                .build()
        );

        write(dw_coder_rank_type_lu
                , ImmutableMap.<String, String>builder()
                .put("id", "2")
                .put("desc", "test coder rank type 2")
                .build()
        );

        for (int i = 0; i < 500; i++) {
            write("INSERT INTO coder(coder_id, handle) VALUES(@cid@, '@ha@');"
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("ha", "user" + i)
                    .build()
            );
        }

        for (int i = 0; i < 20; i++) {
            write(dw_country
                    , ImmutableMap.<String, String>builder()
                    .put("cc", String.valueOf(i + 1))
                    .put("cn", "country" + i)
                    .build()
            );
        }

        for (int i = 0; i < 500; i++) {
            write("INSERT INTO country_coder_rank(coder_id, coder_rank_type_id, algo_rating_type_id, country_code, rank_no_tie) VALUES(@cid@, @coder_rank_type_id@, @alid@, @cc@, @rank_no_tie@);"
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("cc", String.valueOf(r.nextInt(20) + 1))
                    .put("coder_rank_type_id", "2")
                    .put("alid", "3")
                    .put("rank_no_tie", String.valueOf(r.nextInt(1200) + 1))
                    .build()
            );
        }

        for (int i = 0; i < 500; i++) {
            write("INSERT INTO algo_rating(coder_id, algo_rating_type_id, rating) VALUES(@cid@, 3, @rating@);"
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("rating", String.valueOf(r.nextInt(4000) + 1))
//                    .put("rank_no_tie", String.valueOf(r.nextInt(1200)))
                    .build()
            );
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateAchievements() throws IOException {
        r = new Random(System.currentTimeMillis());

        List<Integer> rule_id = ImmutableList.of(109,110,111,112,113,114,115,116,117,118, 121,123,125);
        List<Integer> software_achievement = ImmutableList.of(1, 2, 3, 4, 6, 11, 16, 19, 21);
        List<Integer> more_rule = ImmutableList.<Integer>builder().addAll(rule_id).addAll(software_achievement).build();
        List<Integer> ids = Lists.newArrayList();
        for (Integer id : rule_id) {
            ids.add(id);
        }

        Collections.shuffle(ids);

        for (int i = 0; i < 10; i++) {
            write(tcs_dw_user_achievement_xref
                    , ImmutableMap.<String, String>builder()
                    .put("uid", "132456")
                    .put("rule_id", String.valueOf(ids.get(i)))
                    .put("date", "CURRENT")
                    .build()
            );
        }
        for (Integer id : software_achievement) {
            write(tcs_dw_user_achievement_xref
                    , ImmutableMap.<String, String>builder()
                    .put("uid", "132456")
                    .put("rule_id", String.valueOf(id))
                    .put("date", "CURRENT")
                    .build()
            );
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateMarathonMember() throws IOException {
        r = new Random(System.currentTimeMillis());
        Integer hRating = r.nextInt(4000);
        Integer rating = r.nextInt(hRating);
        Integer lrating = r.nextInt(rating);
        write(dw_language
                , ImmutableMap.<String, String>builder()
                .put("id", "1")
                .put("name", "Java")
                .put("status", "Y")
                .build()
        ).write(dw_coder
                , ImmutableMap.<String, String>builder()
                .put("cid", "132456")
                .put("hn", "heffan")
                .put("lid", "1")
                .build()
        ).write(dw_algo_rating_type
                , ImmutableMap.<String, String>builder()
                .put("id", "3")
                .put("desc", "Marathon Match Rating")
                .build()
        ).write(dw_coder_rank_type_lu
                , ImmutableMap.<String, String>builder()
                .put("id", "2")
                .put("desc", "test coder rank type 2")
                .build()
        ).write(dw_coder_rank
                , ImmutableMap.<String, String>builder()
                .put("cid", "132456")
                .put("coder_rank_type_id", "2")
                .put("percentile", String.valueOf(r.nextDouble() * 100))
                .put("rank", "1")
                .put("alid", "3")
                .build()
        ).write(dw_country
                , ImmutableMap.<String, String>builder()
                .put("cc", "us")
                .put("cn", "United State")
                .build()
        ).write(dw_country_coder_rank
                , ImmutableMap.<String, String>builder()
                .put("cid", "132456")
                .put("rank", "1")
                .put("coder_rank_type_id", "2")
                .put("alid", "3")
                .build()
        ).write(dw_school
                , ImmutableMap.<String, String>builder()
                .put("sid", "1")
                .put("name", "school1")
                .put("cc", "us")
                .build()
        ).write(dw_school_coder_rank
                , ImmutableMap.<String, String>builder()
                .put("cid", "132456")
                .put("alid", "3")
                .put("sid", "1")
                .put("coder_rank_type_id", "2")
                .put("rank_no_tie", String.valueOf(r.nextInt(1500)))
                .put("rank", "1")
                .build()
        ).write(dw_round_type
                , ImmutableMap.<String, String>builder()
                .put("id", "13")
                .put("desc", "Marathon Match")
                .put("alid", "3")
                .build()
        );
        write(dw_contest
                , ImmutableMap.<String, String>builder()
                .put("cid", "2001")
                .put("name", "test contest 2001")
                .put("start", "CURRENT - 5 UNITS DAY")
                .put("end", "CURRENT + 20 UNITS DAY")
                .build()
        );
        for (int i = 0; i < 200; i++) {
            write(dw_round
                    , ImmutableMap.<String, String>builder()
                    .put("rid", String.valueOf(2001 + i))
                    .put("cid", "2001")
                    .put("name", "test round 2001")
                    .put("short_name", "test round 2001")
                    .put("rtid", "13")
                    .put("calid", "4017")
                    .build()
            ).write(dw_long_comp_result
                    , ImmutableMap.<String, String>builder()
                    .put("rid", String.valueOf(i + 2001))
                    .put("coder", "132456")
                    .put("place", String.valueOf(r.nextInt(15) + 1))
                    .put("point", String.valueOf(r.nextDouble() * 1000000))
                    .put("num_submissions", String.valueOf(r.nextInt(35)))
                    .build()
            );
        }

        write(dw_rating
                , ImmutableMap.<String, String>builder()
                .put("cid", "132456")
                .put("rating", String.valueOf(rating))
                .put("vol", String.valueOf(r.nextDouble()))
                .put("num_ratings", String.valueOf(r.nextInt(50) + 10))
                .put("algo_rating_type", "3")
                .put("hrating", String.valueOf(hRating))
                .put("lrating", String.valueOf(lrating))
                .put("last_round", "2200")
                .put("num_comp", String.valueOf(r.nextInt(100) + 20))
                .build()
        );

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
