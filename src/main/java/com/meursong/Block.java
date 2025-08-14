package com.meursong;

import java.util.Date;

/**
 * 블록체인의 개별 블록을 나타내는 클래스
 * 각 블록은 인덱스, 타임스탬프, 데이터, 이전 블록 해시, 현재 해시, nonce 값을 포함합니다.
 */
public class Block {
    private final int index;        // 블록의 순서(인덱스)
    private final long timestamp;   // 블록 생성 시간
    private final String data;      // 블록에 저장된 데이터 (트랜잭션 정보 등)
    private final String previousHash; // 이전 블록의 해시값
    private String hash;      // 현재 블록의 해시값
    private int nonce;        // 채굴에 사용되는 난스(임의의 수)

    /**
     * 새로운 블록을 생성합니다.
     * @param index 블록의 인덱스 (순서)
     * @param data 블록에 저장할 데이터
     * @param previousHash 이전 블록의 해시값
     */
    public Block(int index, String data, String previousHash) {
        this.index = index;
        this.timestamp = new Date().getTime(); // 현재 시간을 밀리초로 설정
        this.data = data;
        this.previousHash = previousHash;
        this.nonce = 0; // 채굴 시작 시 nonce는 0부터 시작
        this.hash = calculateHash(); // 초기 해시 계산
    }

    /**
     * 블록의 해시를 계산합니다.
     * 이전 해시, 타임스탬프, nonce, 데이터를 조합하여 SHA-256 해시를 생성합니다.
     * @return 계산된 해시값 문자열
     */
    public String calculateHash() {
        // 이전 해시 + 타임스탬프 + nonce + 데이터를 결합하여 해시 계산
        return StringUtil.applySha256(
                previousHash +
                    timestamp +
                    nonce +
                    data
        );
    }

    /**
     * 블록을 채굴합니다 (Proof of Work).
     * 지정된 난이도에 맞는 해시(앞자리가 0으로 시작)를 찾을 때까지 nonce를 증가시킵니다.
     * @param difficulty 채굴 난이도 (해시 앞자리 0의 개수)
     */
    public void mineBlock(int difficulty) {
        // 목표 패턴 생성 (예: difficulty=4면 "0000")
        String target = new String(new char[difficulty]).replace('\0', '0');
        
        // 해시 앞자리가 목표 패턴과 일치할 때까지 nonce를 증가시키며 반복
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++; // nonce 값 증가
            hash = calculateHash(); // 새로운 해시 계산
        }
        
        // 채굴 완료 메시지 출력
        System.out.println("블록 채굴 성공! : " + hash);
    }

    /**
     * 블록의 인덱스를 반환합니다.
     * @return 블록 인덱스
     */
    public int getIndex() {
        return index;
    }

    /**
     * 블록의 타임스탬프를 반환합니다.
     * @return 블록 생성 시간 (밀리초)
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * 블록에 저장된 데이터를 반환합니다.
     * @return 블록 데이터
     */
    public String getData() {
        return data;
    }

    /**
     * 이전 블록의 해시를 반환합니다.
     * @return 이전 블록 해시값
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     * 현재 블록의 해시를 반환합니다.
     * @return 현재 블록 해시값
     */
    public String getHash() {
        return hash;
    }

    /**
     * 채굴에 사용된 nonce 값을 반환합니다.
     * @return nonce 값
     */
    public int getNonce() {
        return nonce;
    }
}