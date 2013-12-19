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
public class SRMStatistics extends BaseGenerator {

    @Value("${ongoing.base.path}")
    private String fileBasePath;

    @Value("${dw.coder_level}")
    private String dw_coder_level;

    @Value("${dw.level}")
    private String dw_level;

    @Value("${dw.division}")
    private String dw_division;

    private static final String contestFolder = "Topcoder NodeJS SRM Statistics API Queries";

    private static final String fileName = "test_data.sql";

    @Override
    protected void generate() throws Exception {
        write("database topcoder_dw;");
        write();

        generateTopRankMember(r);

//        generateTopRankSchool(r);

//        generateTopRankCountry(r);

        // srm_member_statistics
//        generateBasic(r);

//        generateOther(r);

//        generateAchievements(r);
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateAchievements(Random r) throws IOException {

        write(dw_user_achievement
                , ImmutableMap.<String, String>builder()
                .put("cid", "3001")
                .put("date", "CURRENT")
                .put("type_id", "5")
                .put("desc", "Algorithm Coder of the Month")
                .build()
        );

        write("database tcs_dw;");
        List<Integer> rule_id = ImmutableList.of(89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 119, 122, 120, 126, 127);
        List<Integer> list = ImmutableList.<Integer>builder().addAll(rule_id).add(1).add(21).add(22).add(51).add(2).add(3).add(6).add(7).add(11).build();
        List<Integer> l = Lists.newArrayList();
        for (Integer id : list) {
            l.add(id);
        }
        Collections.shuffle(l);

        for (int i = 0; i < 20; i++) {
            write(tcs_dw_user_achievement_xref
                    , ImmutableMap.<String, String>builder()
                    .put("uid", "3001")
                    .put("rule_id", String.valueOf(l.get(i)))
                    .put("date", "CURRENT")
                    .build()
            );
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateTopRankCountry(Random r) throws IOException {
        write(dw_algo_rating_type
                , ImmutableMap.<String, String>builder()
                .put("id", "1")
                .put("desc", "TopCoder Rating")
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
                    .put("alid", "1")
                    .put("rank_no_tie", String.valueOf(r.nextInt(1200) + 1))
                    .build()
            );
        }

        for (int i = 0; i < 500; i++) {
            write("INSERT INTO algo_rating(coder_id, algo_rating_type_id, rating) VALUES(@cid@, 1, @rating@);"
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("rating", String.valueOf(r.nextInt(4000) + 1))
//                    .put("rank_no_tie", String.valueOf(r.nextInt(1200)))
                    .build()
            );
        }

    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateTopRankSchool(Random r) throws IOException {

        write(dw_algo_rating_type
                , ImmutableMap.<String, String>builder()
                .put("id", "1")
                .put("desc", "TopCoder Rating")
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
                    .put("cc", "")
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
                    .put("alid", "1")
                    .put("rank_no_tie", String.valueOf(r.nextInt(500)))
                    .build()
            );
        }

        for (int i = 0; i < 500; i++) {
            write("INSERT INTO algo_rating(coder_id, algo_rating_type_id, rating) VALUES(@cid@, 1, @rating@);"
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("rating", String.valueOf(r.nextInt(4000) + 1))
                    .build()
            );
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateTopRankMember(Random r) throws IOException {
        // srm top ranked members
        write(dw_language
                , ImmutableMap.<String, String>builder()
                .put("id", "1")
                .put("name", "Java")
                .put("status", "Y")
                .build()
        );
        for (int i = 0; i < 10; i++) {
            write(dw_country
                    , ImmutableMap.<String, String>builder()
                    .put("cc", String.valueOf(i + 1))
                    .put("cn", "country" + (i + 1))
                    .build()
            );
        }
        for (int i = 0; i < 100; i++) {
            write("INSERT INTO coder(coder_id, handle, status, language_id, country_code) VALUES(@cid@, '@hn@', 'A', @lid@, @cc@);"
                    , ImmutableMap.<String, String>builder()
                    .put("cid", String.valueOf(i + 3001))
                    .put("hn", "testUser" + i)
                    .put("lid", "1")
                    .put("cc", String.valueOf(r.nextInt(10) + 1))
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
                .put("id", "1")
                .put("desc", "TopCoder Rating")
                .build()
        );

        // rating
        for (int i = 0; i < 100; i++) {
            write("INSERT INTO algo_rating(coder_id, algo_rating_type_id, rating) VALUES(@id@, 1, @rating@);"
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
                    .put("alid", "1")
                    .build()
            );
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateOther(Random r) throws IOException {
        List<Integer> divisions = ImmutableList.of(1, 2);
        List<Integer> levels = ImmutableList.of(1,2);

        for (Integer did : divisions) {
            write(dw_division
                    , ImmutableMap.<String, String>builder()
                    .put("did", String.valueOf(did))
                    .put("desc", "test division " + did)
                    .build()
            );
        }

        for (Integer lid : levels) {
            write(dw_level
                    , ImmutableMap.<String, String>builder()
                    .put("lid", String.valueOf(lid))
                    .put("desc", "test level " + lid)
                    .build()
            );
        }

        for (Integer did: divisions) {
            for (Integer lid : levels) {
                Integer submitted = r.nextInt(400) + 100;
                Integer failed = r.nextInt(10);
                Integer sys_test = r.nextInt(20);
                Integer correct = submitted - failed;

                Integer challenges = r.nextInt(500) + 100;
                Integer failed_challenges = r.nextInt(challenges);
                Integer success_challenges = challenges - failed_challenges;

                write(dw_coder_level
                        , ImmutableMap.<String, String>builder()
                        .put("cid", "3001")
                        .put("division_id", String.valueOf(did))
                        .put("level_id", String.valueOf(lid))
                        .put("submitted", String.valueOf(submitted))
                        .put("failed", String.valueOf(failed))
                        .put("sys_test", String.valueOf(sys_test))
                        .put("correct", String.valueOf(correct))
                        .put("challenges", String.valueOf(challenges))
                        .put("failed_challenges", String.valueOf(failed_challenges))
                        .put("success_challenges", String.valueOf(success_challenges))
                        .put("alid", "1")
                        .build()
                );
            }
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void generateBasic(Random r) throws IOException {
        write(dw_coder
                , ImmutableMap.<String, String>builder()
                .put("cid", "132456")
                .put("hn", "heffan")
                .put("lid", "1")
                .build()
        );

        write(dw_coder_rank_type_lu
                , ImmutableMap.<String, String>builder()
                .put("id", "2")
                .put("desc", "test coder rank type 2")
                .build()
        );

        write(dw_language
                , ImmutableMap.<String, String>builder()
                .put("id", "1")
                .put("name", "Java")
                .put("status", "Y")
                .build()
        );

        write(dw_algo_rating_type
                , ImmutableMap.<String, String>builder()
                .put("id", "1")
                .put("desc", "TopCoder Rating")
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

        write(dw_round_type
                , ImmutableMap.<String, String>builder()
                .put("id", "1")
                .put("desc", "Singe Round Match")
                .put("alid", "1")
                .build()
        );

        write(dw_round
                , ImmutableMap.<String, String>builder()
                .put("rid", "2001")
                .put("cid", "2001")
                .put("name", "test round 2001")
                .put("rtid", "1")
                .put("calid", "4017")
                .build()
        );

        Integer rating = r.nextInt(2500) + 1000;
        write(dw_rating
                , ImmutableMap.<String, String>builder()
                .put("cid", "3001")
                .put("rating", String.valueOf(rating))
                .put("vol", String.valueOf(r.nextDouble() * 100))
                .put("num_ratings", String.valueOf(r.nextInt(100) + 20))
                .put("algo_rating_type", "1")
                .put("hrating", String.valueOf(r.nextInt(1000) + rating))
                .put("lrating", String.valueOf(r.nextInt(rating)))
                .put("last_round", "2001")
                .put("num_comp", String.valueOf(r.nextInt(100) + 20))
                .build()
        );

        write(dw_coder_rank
                , ImmutableMap.<String, String>builder()
                .put("cid", "3001")
                .put("coder_rank_type_id", "2")
                .put("rank", "1")
                .put("alid", "1")
                .put("percentile", String.valueOf(r.nextDouble() * 100))
                .build()
        );

        write(dw_school_coder_rank
                , ImmutableMap.<String, String>builder()
                .put("cid", "3001")
                .put("rank", "1")
                .put("coder_rank_type_id", "2")
                .put("alid", "1")
                .build()
        );

        write(dw_country_coder_rank
                , ImmutableMap.<String, String>builder()
                .put("cid", "3001")
                .put("rank", "1")
                .put("coder_rank_type_id", "2")
                .put("alid", "1")
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
