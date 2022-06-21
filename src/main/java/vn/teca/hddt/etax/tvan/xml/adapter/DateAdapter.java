package vn.teca.hddt.etax.tvan.xml.adapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class DateAdapter extends XmlAdapter<String, Date> {
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Overrides the unmarshal method of the class XmlAdapter in order
     * to parse a date of the type LocalDate
     *
     * @param date String
     * @return Date
     * @throws ParseException - throws unmarshal exception
     */
    public Date unmarshal(String date) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        // dd/MM/yyyy HH:mm:ss
        dateFormat.setLenient(false);
        return dateFormat.parse(date);
    }

    /**
     * Overrides the marshal method of the class XmlAdapter in order
     * to convert the passed date to an String
     *
     * @param date Date
     * @return String
     */
    public String marshal(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return dateFormat.format(date);
    }
}
