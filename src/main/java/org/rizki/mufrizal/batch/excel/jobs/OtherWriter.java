package org.rizki.mufrizal.batch.excel.jobs;

import lombok.extern.slf4j.Slf4j;
import org.rizki.mufrizal.batch.excel.domain.Agent;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 21 May 2019
 * @Time 22:14
 * @Project Batch-Excel
 * @Package org.rizki.mufrizal.batch.excel.jobs
 * @File OtherWriter
 */
@Slf4j
@Component
public class OtherWriter implements ItemStreamWriter<Agent> {

    private OutputStream outputStream;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            File file = new File(System.getProperty("user.dir") + "/output.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            this.outputStream = new FileOutputStream(file);
        } catch (Exception e) {
            throw new ItemStreamException(e.getMessage(), e);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
    }

    @Override
    public void close() throws ItemStreamException {
        if (this.outputStream != null) {
            try {
                this.outputStream.close();
            } catch (IOException e) {
                log.error("Cannot close output stream");
            }
        }
    }

    @Override
    public void write(List<? extends Agent> list) throws Exception {
        list.forEach(l -> {
            log.info("Writer Two : {}", l.getKodeAgent() + " - " + l.getNamaAgent() + " - " + l.getAlamat1() + " " + l.getAlamat2());
        });
    }
}