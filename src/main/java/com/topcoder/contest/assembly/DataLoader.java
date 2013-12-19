/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.contest.assembly;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static java.lang.System.*;

/**
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0
 */
public class DataLoader {
    public static final String TCS_CATALOG = "tcs_catalog";

    public static final String INFORMIXOLTP = "informixoltp";

    public static final String TOPCODER_DW = "topcoder_dw";

    public static void main(String[] args) throws Exception {
        Class.forName("com.informix.jdbc.IfxDriver");
        String host = "54.227.30.127";
        String url = "jdbc:informix-sqli://" + host + ":2021/" + INFORMIXOLTP + ":INFORMIXSERVER=informixoltp_tcp";

        String sql = "SELECT * FROM round_type_lu";
        String s2 = "SELECT * FROM algo_rating_type_lu;";

        String destSql = "INSERT INTO round_type_lu(round_type_id, round_type_desc, algo_rating_type_id) VALUES(rtid, 'rtde', alid);";
        String d2 = "INSERT INTO algo_rating_type_lu(algo_rating_type_id, algo_rating_type_desc) VALUES(alid, 'aldesc');";

        Connection con = DriverManager.getConnection(url, "informix", "1nf0rm1x");
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Integer> rtid = Lists.newArrayList();
        while (rs.next()) {
            out.println(destSql
                    .replace("rtid", String.valueOf(rs.getInt("round_type_id")))
                    .replace("rtde", rs.getString("round_type_desc"))
                    .replace("alid", String.valueOf(rs.getInt("algo_rating_type_id")))
            );
            rtid.add(rs.getInt("round_type_id"));
        }
        out.println();
        ps = con.prepareStatement(s2);
        rs = ps.executeQuery();
        while (rs.next()) {
            out.println(d2
                    .replace("alid", String.valueOf(rs.getInt("algo_rating_type_id")))
                    .replace("aldesc", rs.getString("algo_rating_type_desc"))
            );
        }
        out.println();
        out.println(Joiner.on(", ").join(rtid));
    }
}
