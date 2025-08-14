package com.meursong;

/**
 * NoobChain 블록체인 데모 애플리케이션
 * 기본적인 블록체인 생성, 트랜잭션 추가, 무결성 검증을 수행합니다.
 */
public class Main {
    /**
     * 메인 메소드: 블록체인 데모를 실행합니다.
     * @param args 커맨드라인 인수 (사용되지 않음)
     */
    public static void main(String[] args) {
        // 데모 시작 메시지 출력
        System.out.println("=== NoobChain 블록체인 데모 ===\n");
        
        // 블록체인 초기화 안내 메시지
        System.out.println("블록체인을 초기화합니다...");
        // 채굴 난이도 설정 안내
        System.out.println("난이도: 4 (채굴 시 필요한 선행 0의 개수)\n");
        
        // 난이도 4로 새로운 블록체인 생성 (제네시스 블록 포함)
        Blockchain noobChain = new Blockchain(4);
        
        // 첫 번째 트랜잭션 블록 추가
        System.out.println("\n첫 번째 트랜잭션 블록 추가...");
        // Alice가 Bob에게 10 BTC를 송금하는 트랜잭션 블록 추가
        noobChain.addBlock("송금: Alice -> Bob 10 BTC");
        
        // 두 번째 트랜잭션 블록 추가
        System.out.println("\n두 번째 트랜잭션 블록 추가...");
        // Bob이 Charlie에게 5 BTC를 송금하는 트랜잭션 블록 추가
        noobChain.addBlock("송금: Bob -> Charlie 5 BTC");
        
        // 세 번째 트랜잭션 블록 추가
        System.out.println("\n세 번째 트랜잭션 블록 추가...");
        // Charlie가 Alice에게 3 BTC를 송금하는 트랜잭션 블록 추가
        noobChain.addBlock("송금: Charlie -> Alice 3 BTC");
        
        // 완성된 블록체인 정보 출력
        noobChain.printChain();
        
        // 블록체인 무결성 테스트 시작
        System.out.println("\n=== 블록체인 무결성 테스트 ===");
        // 블록체인 무결성 테스트 실행
        testBlockchainIntegrity();
    }
    
    /**
     * 블록체인의 무결성을 테스트합니다.
     * 1. 정상적인 블록체인의 유효성 검증
     * 2. 데이터 변조 시도 및 탐지 테스트
     */
    private static void testBlockchainIntegrity() {
        // 테스트 1: 정상적인 블록체인 유효성 검증
        System.out.println("\n테스트 1: 정상적인 블록체인");
        // 테스트용 블록체인 생성 (난이도 3)
        Blockchain testChain = new Blockchain(3);
        // 테스트 블록들 추가
        testChain.addBlock("Test Block 1");
        testChain.addBlock("Test Block 2");
        // 정상 블록체인의 유효성 검증 결과 출력
        System.out.println("블록체인 유효성: " + (testChain.isChainValid() ? "✓ 유효함" : "✗ 유효하지 않음"));
        
        // 테스트 2: 블록 데이터 변조 및 탐지
        System.out.println("\n테스트 2: 블록 데이터 변조 시도");
        // 변조 테스트용 블록체인 생성
        Blockchain tamperedChain = new Blockchain(3);
        // 원본 데이터로 블록 추가
        tamperedChain.addBlock("Original Data");
        
        // 두 번째 블록(인덱스 1) 참조 (변조 대상)
        Block tamperedBlock = tamperedChain.getChain().get(1);
        // 변조 전 원본 데이터 출력
        System.out.println("원본 데이터: " + tamperedBlock.getData());
        
        // 리플렉션을 사용하여 private 필드에 접근해 데이터 변조 시도
        try {
            // Block 클래스의 private data 필드에 접근
            java.lang.reflect.Field dataField = Block.class.getDeclaredField("data");
            // private 필드 접근 허용
            dataField.setAccessible(true);
            // 데이터 필드 값을 악의적으로 변조
            dataField.set(tamperedBlock, "Tampered Data!");
            // 변조된 데이터 출력
            System.out.println("변조된 데이터: " + tamperedBlock.getData());
        } catch (Exception e) {
            // 리플렉션 오류 처리
            e.printStackTrace();
        }
        
        // 변조된 블록체인의 유효성 검증 (변조 탐지 확인)
        System.out.println("블록체인 유효성: " + (tamperedChain.isChainValid() ? "✓ 유효함" : "✗ 유효하지 않음"));
        
        // 데모 완료 메시지
        System.out.println("\n=== 블록체인 학습 완료! ===");
        // 다음 학습 단계 안내
        System.out.println("다음 단계: roadmap.md 파일을 참고하여 Web3j 연동 또는 Hyperledger Fabric을 학습해보세요!");
    }
}