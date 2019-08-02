# API
  aceproject 신규 입사자 소규모 컨텐츠 API
  
# 사용 도구
  > 스트링 부트  
  > Mybatis  
  > Maven  
  > Mysql  
  
# Scout Reporting API
  ## 스카웃 목록 조회
    1. 스카웃 가능한 PLAYER 목록을 반환
    2. 마지막 무료 갱신 시간을 체크하여 마지막 갱신으로부터 1시간이 지났을 경우, 갱신하여 반환

  ## 영입
    1. 스카웃 목록의 PLAYER를 영입
    2. 영입한 PLAYER는 TEAM이 보유 (= TEAMPLAYER DB에 저장)
    3. 이미 보유한 PLAYER 영입 -> 보유한 PLAYER LEVEL 1 증가
    4. 모든 선수의 영입 비용 : 50ap

  ## 갱신
    1. 갱신은 유료 갱신, 무료 갱신이 존재
    2. 무료 갱신의 경우, 마지막 갱신 시간으로부터 1시간 초과 시 갱신
    3. 유료 갱신의 경우, 50ap 지불 (마지막 갱신 시간에 영향이 끼치지 않음)
    4. 유료 갱신의 경우, OPTION 선택 가능
    
  ## Option 설명
    1. 특정 구단 설정
      - 갱신 시 TEAM이 원하는 구단 PLAYER만 조회 가능
    2. COST 필터
      - 갱신 시 PLAYER의 COST가 5 이상인 PLAYER만 조회 가능
      
    * OPTION 비용
      :: OPTION x 2  ->  150 ap
      :: OPTION x 1  ->  100 ap
      :: DEFAULT     ->   50 ap
    
# TRADE API
  ## TRADE 목록 조회
    1. TEAM이 소유하고 있는 PLAYER 목록을 반환
  
  ## TRADE
    1. TEAM이 소유한 PLAYER(2명 ~ 4명)을 소모 -> 새로운 PLAYER 영입 (=TRADE)
    2. OPTION 설정 가능 (무료, 유료)
    3. 재료로 사용한 선수는 TRADE를 통해 등장하지 않음
    4. 새로운 PLAYER는 재료의 조합을 통해 확률 적용 후 등장
    
  ## OPTION 설명
    1. YEAR 설정 (무료)
      - TRADE 시 설정한 년도의 PLAYER만 영입 (최소 2개 ~ 최대 모든 년도 선택 가능)
    2. 등급 확률 증가 (유료 - 10cash)
      - 기존 조합법 확률에서 10% 증가 시킨 확률을 적용하여, 새로운 PLAYER 영입
    3. COST 업 (유료 - 100ap)
      - 영입할 PLAYER의 COST를 확정적으로 한 단계 증가 후 영입
      
  ## 조합법
    :: ACE x 4      ->  ACE 100%
    :: MONSTER x 4  ->  MONSTER 60%
    :: MONSTER x 3  ->  MONSTER 55%
    :: MONSTER x 2  ->  MONSTER 50%
    :: SPECIAL x 4  ->  SPECIAL 70%
    :: SPECIAL x 3  ->  SPECIAL 65%
    :: SPECIAL x 2  ->  SPECIAL 60%
    :: DEFAULT      ->  MONSTER, SPECAIL, NORMAL 중 하나
    * 확률 실패, 조합법에 존재하지 않는 조합 시 DEFAULT
    
    :: 트레이드 후 나올 선수의 COST = 재료들의 COST 평균(소수점 시 올림)
    :: option: cost UP 시 원래 cost에서 1up 후 등장
    :: 8 cost x 4   ->  나와야 할 등급에서 20% 확률로 등급 업
    :: 8 cost x 3   ->  나와야 할 등급에서 15% 확률로 등급 업
    :: 8 cost x 2   ->  나와야 할 등급에서 10% 확률로 등급 업
    
    
