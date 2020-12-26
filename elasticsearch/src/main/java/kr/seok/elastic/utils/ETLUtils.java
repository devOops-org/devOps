package kr.seok.elastic.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.seok.elastic.domain.HospitalEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * CSV 데이터 추출 및 ES 적재
 */
public class ETLUtils {

    public static final String CREATE_DOCUMENT_URL = "http://localhost:9200/hospital_index/_doc/";
    public static final String FILE_PATH = "files/seoul_hospital_position_info_utf8.csv";

    public ETLUtils() {
    }

    public static void main(String[] args) throws IOException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + FILE_PATH);
        if (!file.exists()) {
            System.out.println("파일 없음");
            return;
        }
        BufferedReader reader = //new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), ""));
                Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

        String line;
        int cnt = 0;
        List<HospitalEntity> list = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if (cnt != 0) {
                line = line.replaceAll("\"", "");
                String[] split = line.split(",");

//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.KOREA);
//                LocalDateTime dateTime = LocalDateTime.parse(split[33], formatter);
//                System.out.println(dateTime.toLocalDate() + " " + dateTime.toLocalTime());

//                DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.KOREA);
//                ZonedDateTime timestamp = ZonedDateTime.parse(LocalDateTime.now().toString(), timestampFormatter);

                /* 필드가 34개 */
                list.add(HospitalEntity.builder()
                        .id(split[0])
                        .addr(split[1])
                        .hosCate(split[2])
                        .hosCateNm(split[3])

                        .fstAidMedicInsCd(split[4])
                        .fstAidMedicInsNm(split[5])
                        .edOperYn(split[6])

                        .etc(split[7])
                        .operDescDt(split[8])

                        .simpleMap(split[9])
                        .operNm(split[10])
                        .phone1(split[11])
                        .edPhone(split[12])

                        .operHourMonC(split[13])
                        .operHourTueC(split[14])
                        .operHourWedC(split[15])
                        .operHourThuC(split[16])
                        .operHourFriC(split[17])
                        .operHourSatC(split[18])
                        .operHourSunC(split[19])
                        .operHourHolC(split[20])

                        .operHourMonS(split[21])
                        .operHourTueS(split[22])
                        .operHourWedS(split[23])
                        .operHourThuS(split[24])
                        .operHourFriS(split[25])
                        .operHourSatS(split[26])
                        .operHourSunS(split[27])
                        .operHourHolS(split[28])

                        .zipCode1(split[29])
                        .zipCode2(split[30])

                        .lon(Double.parseDouble(split[31]))
                        .lat(Double.parseDouble(split[32]))
                        .date(split[33])
//                        .timestamp(timestamp)
                        .build());
            }
            cnt++;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        int i = 1;
        System.out.println("엘라스틱 서치에 데이터 넣기");
        for (HospitalEntity item : list) {
            /* HttpClient Request */
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(CREATE_DOCUMENT_URL + item.getId());

            String valueAsString = objectMapper.writeValueAsString(item);
            System.out.println("[LOG] [INPUT_DATA] [" + valueAsString + "]");
            StringEntity entity = new StringEntity(valueAsString, Charset.forName(StandardCharsets.UTF_8.name()));
            httpPut.setEntity(entity);
//            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPut);
            System.out.println(response.toString());
            client.close();
            i++;
        }
    }
}
