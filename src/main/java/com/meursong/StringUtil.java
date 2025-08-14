package com.meursong;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 문자열 유틸리티 클래스
 * SHA-256 해시 알고리즘을 사용하여 문자열을 해시값으로 변환하는 기능을 제공합니다.
 */
public class StringUtil {
    
    /**
     * 입력된 문자열에 SHA-256 해시 알고리즘을 적용하여 16진수 해시 문자열을 반환합니다.
     * @param input 해시화할 입력 문자열
     * @return SHA-256 해시값 (16진수 문자열)
     * @throws RuntimeException 해시 처리 중 오류 발생 시
     */
    public static String applySha256(String input) {
        try {
            // SHA-256 다이제스트 인스턴스 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // 입력 문자열을 UTF-8 바이트로 변환하여 해시 계산
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            
            // 바이트 배열을 16진수 문자열로 변환
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                // 각 바이트를 16진수로 변환
                String hex = Integer.toHexString(0xff & hash[i]);
                // 한 자리수인 경우 앞에 0을 추가하여 두 자리로 만들기
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            // 최종 16진수 해시 문자열 반환
            return hexString.toString();
            
        } catch (Exception e) {
            // SHA-256 처리 오류 시 RuntimeException으로 래핑
            throw new RuntimeException(e);
        }
    }
}