package kr.seok.elastic.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.seok.elastic.domain.HospitalDto;
import kr.seok.elastic.domain.HospitalEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * CSV 데이터 추출 및 ES 적재
 */
@Slf4j
public class ETLUtils {

    public static final String CREATE_DOCUMENT_URL = "http://localhost:9200/hospital_index/_doc/";
    public static final String FILE_PATH = "files/seoul_hospital_position_info_utf8.csv";

    protected ETLUtils() { }

    public ETLUtils(String filePath) {
        postESHospitalData(filePath);
    }

    /* 병원 데이터 ES에 넣는 로직 */
    public void postESHospitalData(String filePath) {
        try {
            /* 파일 호출 */
            File file = getFile(filePath);

            if (!file.exists()) throw new FileNotFoundException();

            /* 파일 내용 가져오기 */
            List<HospitalEntity> hospitalEntities = getFileData(file);

            if(hospitalEntities.isEmpty()) log.info("데이터가 존재하지 않습니다.");

            /* 엘라스틱 서치에 저장 */
            postHttpClient(hospitalEntities);

        } catch (FileNotFoundException e) { log.info("파일이 존재하지 않습니다.");
        } catch (IOException e) {           log.info("파일 읽는 문제 발생");
        }
    }
    /* 파일 읽기 메서드 */
    public File getFile(String resourceLocation) throws FileNotFoundException {
        return ResourceUtils.getFile(resourceLocation);
    }

    /* 파일 내용을 읽어 DTO 에 저장 */
    public List<HospitalEntity> getFileData(File file) throws IOException {
        BufferedReader reader =
                //new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), ""));
                Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

        String line; int cnt = 0;
        List<HospitalEntity> list = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if (cnt != 0) {

                line = line.replaceAll("\"", ""); /* 데이터에 ' " '가 존재하여 제거 */
                String[] split = line.split(","); /* csv 파일이므로 ','를 기준으로 데이터 split */

                /* 필드가 34개 */
                list.add(HospitalDto.toEntity(split));
            }
            cnt++;
        }
        return list;
    }

    /* ES HttpClient Put API 호출 */
    private void postHttpClient(List<HospitalEntity> hospitalEntities) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        int i = 1;
        System.out.println("엘라스틱 서치에 데이터 넣기");
        for (HospitalEntity item : hospitalEntities) {

            CloseableHttpClient client = HttpClients.createDefault(); /* HttpClient Request */
            HttpPut httpPut = new HttpPut(CREATE_DOCUMENT_URL + item.getId());

            String valueAsString = objectMapper.writeValueAsString(item);

            log.info("[LOG] [INPUT_DATA] [{}]", valueAsString);

            StringEntity entity = new StringEntity( /* convert map to json 된 데이터를 utf8로 인코딩하는 작업 필요 */
                    valueAsString,
                    Charset.forName(StandardCharsets.UTF_8.name())
            );
            httpPut.setEntity(entity);
            httpPut.setHeader("content-type", APPLICATION_JSON.toString());

            CloseableHttpResponse response = client.execute(httpPut);
            if(response.getStatusLine().getStatusCode() == 400) {
                throw new IllegalArgumentException("에러");
            }
            log.info("[LOG] [응답 값] [{}]", response);

            client.close();
            i++;
        }
    }

    public static void main(String[] args) {
        new ETLUtils(ResourceUtils.CLASSPATH_URL_PREFIX + FILE_PATH);
    }
}
