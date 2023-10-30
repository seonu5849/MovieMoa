package org.zerock.myapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.json.*;
import org.zerock.myapp.mapper.MovieMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor

@Service
public class MovieJsonServiceImpl implements MovieJsonService {

    private final MovieMapper movieMapper;

    // API 키 값 주입 받기
    @Value("${tmdb.api.key}")
    private String apiKey;

    // 영화 API의 기본 URL
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    // JSON 객체를 Java 객체로 변환하기 위한 Jackson 라이브러리의 ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // 모든 장르 정보를 가져오는 메서드
    public Integer jsonToGenres() throws IOException {
        String apiUrl = "https://api.themoviedb.org/3/genre/movie/list?language=ko-kr&api_key=" + apiKey;
        // 장르 정보를 가져오기 위한 API URL 설정

        GenreAll genreAll = fetchData(apiUrl, GenreAll.class);
        // API 호출을 통해 장르 정보를 가져오는 메서드 호출

        // DB 넣기
        List<GenreInfo> genres = genreAll.getGenres();
        // 가져온 장르 정보 리스트

        Integer affectedRows = 0;
        // DB에 영향을 미친 행의 수를 추적하기 위한 변수 선언

        for (GenreInfo genre : genres){
            affectedRows = this.movieMapper.insertGenre(genre);
            // 장르 정보를 DB에 저장
        }

        log.info("\t+ affectedRows : {}", affectedRows);
        // 콘솔에 DB에 저장된 행의 수 출력

        return affectedRows;
        // 영향받은 행의 총 수를 반환
    } // jsonToGenres

    // 주어진 범위의 영화 정보(여러개)를 가져오는 메서드
    public Integer jsonToMovies(Long startNum, Long endNum) {
        List<Movies> movies = fetchMultipleData(startNum, endNum, "", Movies.class);

        movies.forEach(log::info);
        Integer affectedRows = null;

        // DB 저장
        for(Movies movie : movies) {
            affectedRows = movieMapper.insertMovie(movie); // 영화 데이터 넣기

            for (GenreInfo genre : movie.getGenres()) { // 영화별 장르 넣기
                affectedRows = this.movieMapper.insertMovieOfGenre(movie.getId(), genre.getId());
            }
        }

        log.info("\t+ affectedRows : {}", affectedRows);
        return affectedRows;
    } // jsonToMovies

    // 주어진 범위의 영화의 제작진 정보(여러개)를 가져오는 메서드
    public Integer jsonToMovieCredits(Long startNum, Long endNum) {
        List<Credits> movieCreditsList = fetchMultipleData(startNum, endNum, "/credits", Credits.class);
        // API에서 startNum부터 endNum까지의 영화 제작진 정보를 가져오는 메서드 호출

        Integer affectedRows = 0;
        // DB에 영향을 미친 행의 수를 추적하기 위한 변수 선언

        for (Credits movieCredits : movieCreditsList) {
            // 가져온 영화 제작진 정보 리스트를 순회
            log.info("\t+ movieCredits : {}", movieCredits);
            // 콘솔에 영화 제작진 정보 출력

            // 상위 5명의 배우 정보 추출
            List<Cast> top5Cast = movieCredits.getCast().subList(0, Math.min(5, movieCredits.getCast().size()));

            // 감독 및 작가 정보만 필터링
            List<Crew> relevantCrew = movieCredits.getCrew().stream()
                    .filter(crew -> ("Directing".equals(crew.getDepartment()) && "Director".equals(crew.getJob()))
                            || "Writing".equals(crew.getDepartment()))
                    .collect(Collectors.toList());

            log.info("\t+ Top 5 Cast : {}", top5Cast);
            log.info("\t+ Directing or Writing Crew : {}", relevantCrew);
            // 콘솔에 상위 5명의 배우와 감독 및 작가 정보 출력

            // DB에 저장하는 부분 추가
            for (Cast cast : top5Cast) {
                affectedRows += movieMapper.insertCast(movieCredits.getMovieId(), cast);
                // 상위 5명의 배우 정보를 DB에 저장
            }
            for (Crew crew : relevantCrew) {
                affectedRows += movieMapper.insertCrew(movieCredits.getMovieId(), crew);
                // 감독 및 작가 정보를 DB에 저장
            }
        }

        return affectedRows;
        // 영향받은 행의 총 수를 반환
    } // jsonToMovieCredits

    // 주어진 범위의 영화의 관람 등급 정보(여러개)를 가져오는 메서드
    public Integer jsonToCertification(Long startNum, Long endNum) {

        // 영화 관람 등급 정보를 리스트로 가져옴.
        List<ReleaseDates> movieCertification = fetchMultipleData(startNum, endNum, "/release_dates", ReleaseDates.class);

        Integer[] affectedRows = {0};

        // 각 영화에 대한 관람 등급 정보를 처리함.
        movieCertification.forEach(movie -> {
            // 한국 관람 등급 정보만 필터링.
            List<CertificationResults> koreanReleaseDates = movie.getResults().stream()
                    .filter(releaseDate -> "KR".equals(releaseDate.getIso_3166_1()))
                    .collect(Collectors.toList());

            // 필터링된 정보를 데이터베이스에 저장.
            koreanReleaseDates.forEach(releaseDate -> {
                String isoCode = releaseDate.getIso_3166_1();
                String certification = releaseDate.getRelease_dates().get(0).getCertification();

                affectedRows[0] += movieMapper.insertCertification(movie.getMovieId(), isoCode, certification);
            });
        });

        return affectedRows[0];
    } // jsonToCertification

    // 주어진 영화 ID에 해당하는 영화 정보(1개)를 가져오는 메서드
    public Movies jsonToMovie(Long movieId) throws IOException {
        return fetchData(movieId, "", Movies.class);
    } // jsonToMovie

    // 주어진 영화 ID에 해당하는 영화의 제작진 정보를 가져오는 메서드
    public Credits jsonToMovieCredit(Long movieId) throws IOException {
        return fetchData(movieId, "/credits", Credits.class);
    } // jsonToMovieCredits

    // 영화 ID와 엔드포인트를 이용해 특정 데이터를 가져오는 내부 메서드
    private <T> T fetchData(Long movieId, String endpoint, Class<T> clazz) throws IOException {
        // 영화 ID, 엔드포인트, 언어, API 키를 사용하여 API 요청 URL 구성
        String apiUrl = BASE_URL + movieId + endpoint + "?language=ko-kr&api_key=" + apiKey;
        // 구성한 URL과 클래스 타입을 이용해 실제 데이터를 가져오는 fetchData 호출
        return fetchData(apiUrl, clazz);
    } // fetchData

    // 주어진 URL에서 데이터를 가져와 Java 객체로 변환하는 내부 메서드
    private <T> T fetchData(String apiUrl, Class<T> clazz) throws IOException {
        // 주어진 API URL에서 JSON 문자열을 가져옴
        String json = getJsonFromApi(apiUrl);
        // 가져온 JSON 문자열을 Jackson 라이브러리를 사용하여 Java 객체로 변환
        return objectMapper.readValue(json, clazz);
    } // fetchData

    // 주어진 범위와 엔드포인트를 사용하여 여러 개의 데이터를 가져오는 내부 메서드
    private <T> List<T> fetchMultipleData(Long startNum, Long endNum, String endpoint, Class<T> clazz) {
        // 결과를 저장할 리스트 초기화
        List<T> resultList = new ArrayList<>();
        // 시작 번호부터 종료 번호 전까지 반복
        for (Long i = startNum; i < endNum; i++) {
            try {
                // 각 ID에 대해 fetchData 메서드를 호출하여 데이터를 가져옴.
                // endpoint는 추가적인 URL 경로, clazz는 반환받을 객체의 클래스 타입
                T result = fetchData(i, endpoint, clazz);
                // 가져온 데이터를 결과 리스트에 추가
                resultList.add(result);
            } catch (IOException e) {
                // 데이터를 가져오는 과정에서 IOException 발생 시 로그에 오류 메시지 기록
                log.error("ID " + i + "에 대한 데이터를 가져오는데 실패했습니다. 오류: " + e.getMessage());
            }
        }
        // 모든 데이터를 가져온 결과 리스트 반환
        return resultList;
    } // fetchMultipleData

    // 주어진 URL에서 JSON 문자열을 가져오는 메서드
    private String getJsonFromApi(String apiUrl) throws IOException {
        // 주어진 URL 문자열로 URL 객체 생성
        URL url = new URL(apiUrl);
        HttpURLConnection conn = null;
        try {
            // URL 객체를 사용하여 HTTP 연결 열기
            conn = (HttpURLConnection) url.openConnection();
            // HTTP 요청 방식을 GET으로 설정
            conn.setRequestMethod("GET");
            // API URL 로깅
            log.info("\t+ apiUrl : {}", apiUrl);

            // HTTP 응답 코드 가져오기
            int responseCode = conn.getResponseCode();
            // 응답 코드가 HTTP OK(200)이면
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // BufferedReader를 사용하여 응답 데이터 읽기 (UTF-8 문자셋 사용)
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    // 응답 데이터를 한 줄씩 읽어 StringBuilder에 추가
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    // 최종적으로 읽은 데이터 문자열 반환
                    return content.toString();
                }
            } else {
                // 응답 코드가 HTTP OK가 아닌 경우 예외 발생
                throw new IOException("HTTP 요청이 실패했습니다. 응답 코드: " + responseCode);
            }
        } finally {
            // 연결 객체가 null이 아니면 연결 종료
            if (conn != null) {
                conn.disconnect();
            }
        }
    } // getJsonFromApi

} // end class
