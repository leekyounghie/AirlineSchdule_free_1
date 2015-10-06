package com.starnamu.airlineschdule.comm;

/**
 * Created by starnamu on 2015-05-08.
 */
public interface CommonConventions {

    String URLHADE = "http://openapi.airport.kr/openapi/service/StatusOfPassengerFlights";

    String SERVICEKEY = "?ServiceKey=RN5il12RYM%2FXFWaIm8otCbez%2B5W1YxN91ZzBtYx" +
            "4u3hh24IgLuMAr5LEvByuM62KPv7l8Y4qbNUy0AgE2YtWHw%3D%3D";
    String PARRIVALS = "/getPassengerArrivals";
    String PDEPARTURES = "/getPassengerDepartures";

    String[] AIRLINENAME = {"중국동방항공", "사천항공", "싱가포르항공", "에바항공", "산동항공",
            "에어캐나다", "오로라항공", "델타항공", "에어아시아 제스트항공", "전일본공수", "에어부산",
            "에어 마카오", "블라디보스토크", "S7 항공", "상하이항공"};

    String[] ALLAIRLINENAME = {"아시아나항공", "중국동방항공", "사천항공", "싱가포르항공",
            "에바항공", "산동항공", "에어캐나다", "오로라항공", "델타항공", "에어아시아 제스트항공",
            "전일본공수", "에어부산", "에어 마카오", "블라디보스토크", "S7 항공", "상하이항공"};

    String[] PARSERITEMGROUP = {"airline", "airport", "airportCode", "flightId", "scheduleDateTime",
            "estimatedDateTime", "chkinrange", "gatenumber", "remark", "carousel", "ADStat"};

    String AlarmTableName = "AlarmTableName";
    String SchduleDbName = "AlarmDB.db";
    int dbVersion = 1;
}
