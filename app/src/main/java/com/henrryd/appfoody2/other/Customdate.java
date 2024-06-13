package com.henrryd.appfoody2.other;

import android.os.Build;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Customdate {
    public static LocalDate getLocaldatenow()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.now();
        }
        return null;
    }

    public static String getLocaldatetimenow()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.now().format(formatter).toString();
        }
        return null;
    }

    public static LocalDateTime getLocaldatetimenoww()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.now();
        }
        return null;
    }





    public static LocalDate getLocaldate(int nam, int thang, int ngay)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.of(nam, thang, ngay);
        }
        return null;
    }

    public static LocalDate getLocaldate(String tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.parse(tmp);
        }
        return null;
    }

    public static int getYear(LocalDate tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return  tmp.getYear();
        }
        return 0;
    }

    public static int getMonth(LocalDate tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tmp.getMonth().getValue();
        }
        return 0;
    }

    public static int getDay(LocalDate tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tmp.getDayOfMonth();
        }
        return 0;
    }

    public static String ConvertDate(LocalDate tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tmp.getDayOfMonth() + "-" + tmp.getMonth().getValue() + "-" +tmp.getYear();
        }
        return null;
    }

    public static String ConvertDate(String tmp1)
    {
        return tmp1.substring(8,10) + "-" + tmp1.substring(5,7) + "-" + tmp1.substring(0,4);
    }


    public static LocalTime getLocaltimenow()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
        }
        return null;
    }

    public static LocalTime getLocaltime(int hh, int mm, int ss)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalTime.of(hh, mm, ss);
        }
        return null;
    }

    public static LocalTime getLocaltime(String tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalTime.parse(tmp);
        }
        return null;
    }

    public static int getHour(LocalTime tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tmp.getHour();
        }
        return 0;
    }

    public static int getMinute(LocalTime tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tmp.getMinute();
        }
        return 0;
    }

    public static int getSecond(LocalTime tmp)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tmp.getSecond();
        }
        return 0;
    }

    public static String ConvertTime(LocalTime tmp)
    {
        String tmp1 = tmp.toString();
        String tmp2 = "";
        for (int i = tmp1.length() - 1; i >= 0; i--) {
            tmp2 += tmp1.charAt(i);
        }
        return tmp2;
    }

    public static String ConvertTime(String tmp1)
    {
        String tmp2 = "";
        for (int i = tmp1.length() - 1; i >= 0; i--) {
            tmp2 += tmp1.charAt(i);
        }
        return tmp2;
    }
}
