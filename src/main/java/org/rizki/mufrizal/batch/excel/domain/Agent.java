package org.rizki.mufrizal.batch.excel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 21 May 2019
 * @Time 21:45
 * @Project Batch-Excel
 * @Package org.rizki.mufrizal.batch.excel.domain
 * @File Agent
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Agent {
    private String kodeAgent;
    private String namaAgent;
    private String alamat1;
    private String alamat2;
}