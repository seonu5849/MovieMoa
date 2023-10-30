package org.zerock.myapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.json.Credits;
import org.zerock.myapp.domain.json.GenreAll;
import org.zerock.myapp.domain.json.Movies;
import org.zerock.myapp.domain.json.ReleaseDates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class JsonToObject {

    // API 키 값 주입 받기
    @Value("${tmdb.api.key}")
    private String apiKey;

    // 영화 API의 기본 URL
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    // JSON 객체를 Java 객체로 변환하기 위한 Jackson 라이브러리의 ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // 주어진 영화 ID에 해당하는 영화 정보(1개)를 가져오는 메서드
    public Movies jsonToMovie(Long movieId) throws IOException {
        return fetchData(movieId, "", Movies.class);
    } // jsonToMovie

    // 주어진 영화 ID에 해당하는 영화의 제작진 정보를 가져오는 메서드
    public Credits jsonToMovieCredits(Long movieId) throws IOException {
        return fetchData(movieId, "/credits", Credits.class);
    } // jsonToMovieCredits

    // 주어진 범위의 영화 정보(여러개)를 가져오는 메서드
    public List<Movies> jsonToMovies(Long startNum, Long endNum) {
        return fetchMultipleData(startNum, endNum, "", Movies.class);
    } // jsonToMovies

    // 주어진 범위의 영화의 제작진 정보(여러개)를 가져오는 메서드
    public List<Credits> jsonToMovieCredits(Long startNum, Long endNum) {
        return fetchMultipleData(startNum, endNum, "/credits", Credits.class);
    } // jsonToMovieCredits

    // 모든 장르 정보를 가져오는 메서드
    public GenreAll jsonToGenres() throws IOException {
        String apiUrl = "https://api.themoviedb.org/3/genre/movie/list?language=ko-kr&api_key=" + apiKey;
        return fetchData(apiUrl, GenreAll.class);
    } // jsonToGenres

    // 주어진 범위의 영화의 관람 등급 정보(여러개)를 가져오는 메서드
    public List<ReleaseDates> jsonToCertification(Long startNum, Long endNum) {
        return fetchMultipleData(startNum, endNum, "/release_dates", ReleaseDates.class);
    } // jsonToCertification

    // 영화 ID와 엔드포인트를 이용해 특정 데이터를 가져오는 내부 메서드
    private <T> T fetchData(Long movieId, String endpoint, Class<T> clazz) throws IOException {
        String apiUrl = BASE_URL + movieId + endpoint + "?language=ko-kr&api_key=" + apiKey;
        return fetchData(apiUrl, clazz);
    } // fetchData

    // 주어진 URL에서 데이터를 가져와 Java 객체로 변환하는 내부 메서드
    private <T> T fetchData(String apiUrl, Class<T> clazz) throws IOException {
        String json = getJsonFromApi(apiUrl);
        return objectMapper.readValue(json, clazz);
    } // fetchData

    // 주어진 범위와 엔드포인트를 사용하여 여러 개의 데이터를 가져오는 내부 메서드
    private <T> List<T> fetchMultipleData(Long startNum, Long endNum, String endpoint, Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        for (Long i = startNum; i < endNum; i++) {
            try {
                T result = fetchData(i, endpoint, clazz);
                resultList.add(result);
            } catch (IOException e) {
                log.error("ID " + i + "에 대한 데이터를 가져오는데 실패했습니다. 오류: " + e.getMessage());
            }
        }
        return resultList;
    } // fetchMultipleData

    // 주어진 URL에서 JSON 문자열을 가져오는 메서드
    private String getJsonFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            log.info("\t+ apiUrl : {}", apiUrl);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {  // 문자셋을 UTF-8로 지정
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    return content.toString();
                }
            } else {
                throw new IOException("HTTP 요청이 실패했습니다. 응답 코드: " + responseCode);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    } // getJsonFromApi

} // end class
