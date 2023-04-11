package com.sushi.members.jpa.util;

import com.sushi.members.jpa.entity.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvFileGenerator {
    public void writePersonToCsv(List<Person> personList, Writer writer) {

        try{
            writer.write('\ufeff');
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            printer.printRecord("用户编号","用户名","电话号码","微信号码","邮箱地址","剩余积分","注册日期","用户状态");
            for(Person person : personList) {
                printer.printRecord(person.getId(),person.getUsername(),person.getPhoneNum(),person.getWechat(),person.getEmail(),person.getPoint(),person.getRegisterDate(),person.getStatus());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
