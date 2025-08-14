package com.meursong;

import java.util.ArrayList;

/**
 * 블록체인 클래스
 * 블록들을 연결된 체인 형태로 관리하고, 블록 추가, 유효성 검증, 채굴 등의 기능을 제공합니다.
 */
public class Blockchain {
    private final ArrayList<Block> chain; // 블록들을 저장하는 체인
    private final int difficulty;         // 채굴 난이도 (해시 앞자리 0의 개수)

    /**
     * 지정된 난이도로 새로운 블록체인을 생성합니다.
     * 제네시스 블록(Genesis Block)을 자동으로 생성하고 채굴합니다.
     * @param difficulty 채굴 난이도
     */
    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = difficulty;
        
        // 제네시스 블록 (최초 블록) 생성 및 채굴
        Block genesisBlock = new Block(0, "Genesis Block", "0");
        genesisBlock.mineBlock(difficulty); // 제네시스 블록도 채굴 필요
        chain.add(genesisBlock); // 체인에 추가
    }

    /**
     * 체인의 가장 마지막 블록을 반환합니다.
     * @return 가장 최근에 추가된 블록
     */
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1); // 마지막 인덱스의 블록 반환
    }

    /**
     * 체인에 새로운 블록을 추가합니다.
     * 이전 블록에 대한 참조를 포함하여 체인 무결성을 유지합니다.
     * @param data 블록에 저장할 데이터
     */
    public void addBlock(String data) {
        // 이전 블록 참조 가져오기
        Block previousBlock = getLatestBlock();
        
        // 새로운 블록 생성 (인덱스 +1, 데이터, 이전 블록 해시)
        Block newBlock = new Block(
            previousBlock.getIndex() + 1,
            data,
            previousBlock.getHash()
        );
        
        // 블록 채굴 (지정된 난이도에 맞는 해시 찾기)
        newBlock.mineBlock(difficulty);
        
        // 체인에 블록 추가
        chain.add(newBlock);
    }

    /**
     * 블록체인의 무결성을 검증합니다.
     * 1. 각 블록의 해시가 올바른지 확인
     * 2. 블록 간 연결이 유효한지 확인
     * 3. 모든 블록이 올바르게 채굴되었는지 확인
     * @return 체인의 유효성 여부 (true: 유효, false: 무효)
     */
    public boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        // 채굴 난이도에 맞는 목표 패턴 생성 (예: "0000")
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        // 두 번째 블록부터 검증 (제네시스 블록은 제외)
        for (int i = 1; i < chain.size(); i++) {
            currentBlock = chain.get(i);     // 현재 검증 중인 블록
            previousBlock = chain.get(i - 1); // 바로 이전 블록

            // 검증 1: 현재 블록의 저장된 해시와 재계산 해시가 일치하는지 확인
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("현재 블록의 해시가 일치하지 않습니다!");
                return false;
            }

            // 검증 2: 이전 블록과 현재 블록 간 연결이 올바른지 확인
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                System.out.println("이전 블록의 해시가 일치하지 않습니다!");
                return false;
            }

            // 검증 3: 블록이 올바르게 채굴되었는지 (난이도 조건 만족) 확인
            if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                System.out.println("이 블록은 채굴되지 않았습니다!");
                return false;
            }
        }
        // 모든 검증 통과 시 체인 유효
        return true;
    }

    /**
     * 등록된 모든 블록의 상세 정보를 출력합니다.
     * 블록체인의 전체 현황과 유효성을 확인할 수 있습니다.
     */
    public void printChain() {
        System.out.println("\n=== 블록체인 현황 ===");
        
        // 모든 블록의 정보를 순차적으로 출력
        for (Block block : chain) {
            System.out.println("\n블록 #" + block.getIndex());
            System.out.println("타임스탬프: " + block.getTimestamp());
            System.out.println("데이터: " + block.getData());
            System.out.println("이전 해시: " + block.getPreviousHash());
            System.out.println("현재 해시: " + block.getHash());
            System.out.println("Nonce: " + block.getNonce());
        }
        
        // 체인 전체의 유효성 검증 결과 출력
        System.out.println("\n블록체인 유효성: " + (isChainValid() ? "유효함 ✓" : "유효하지 않음 ✗"));
    }

    /**
     * 블록체인의 전체 체인을 반환합니다.
     * @return 블록들의 리스트
     */
    public ArrayList<Block> getChain() {
        return chain; // 체인 리스트 반환
    }

    /**
     * 설정된 채굴 난이도를 반환합니다.
     * @return 채굴 난이도
     */
    public int getDifficulty() {
        return difficulty; // 난이도 값 반환
    }
}