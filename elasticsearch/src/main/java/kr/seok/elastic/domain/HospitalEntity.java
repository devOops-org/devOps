package kr.seok.elastic.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@Builder
public class HospitalEntity {
    @Id @GeneratedValue
    @Column(name = "ORG_ID") /* 기관ID */
    private String id;
    @Column(name = "ADDRESS") /* 주소 */
    private String addr;
    @Column(name = "HOS_CATE") /* 병원분류 */
    private String hosCate;
    @Column(name = "HOS_CATE_NM") /* 병원분류명 */
    private String hosCateNm;
    @Column(name = "FST_AID_MEDIC_INS_CD") /* 응급의료기관코드 */
    private String fstAidMedicInsCd;
    @Column(name = "FST_AID_MEDIC_INS_NM") /* 응급의료기관코드명 */
    private String fstAidMedicInsNm;
    @Column(name = "ED_OPER_YN") /* 응급실운영여부(1/2) */
    private String edOperYn;
    @Column(name = "ETC") /* 비고 */
    private String etc;
    @Column(name = "OPER_DESC_DT") /* 기관설명상세 */
    private String operDescDt;
    @Column(name = "SIMPLE_MAP") /* 간이약도 */
    private String simpleMap;
    @Column(name = "OPER_NM") /* 기관명 */
    private String operNm;
    @Column(name = "PHONE1") /* 대표전화1 */
    private String phone1;
    @Column(name = "ED_PHONE") /* 응급실전화 */
    private String edPhone;

    @Column(name = "OPER_HOUR_MON_C") /* 진료시간(월요일)C */
    private String operHourMonC;
    @Column(name = "OPER_HOUR_TUE_C") /* 진료시간(화요일)C */
    private String operHourTueC;
    @Column(name = "OPER_HOUR_WED_C") /* 진료시간(수요일)C */
    private String operHourWedC;
    @Column(name = "OPER_HOUR_THU_C") /* 진료시간(목요일)C */
    private String operHourThuC;
    @Column(name = "OPER_HOUR_FRI_C") /* 진료시간(금요일)C */
    private String operHourFriC;
    @Column(name = "OPER_HOUR_SAT_C") /* 진료시간(토요일)C */
    private String operHourSatC;
    @Column(name = "OPER_HOUR_SUN_C") /* 진료시간(일요일)C */
    private String operHourSunC;
    @Column(name = "OPER_HOUR_HOL_C") /* 진료시간(공휴일)C */
    private String operHourHolC;

    @Column(name = "OPER_HOUR_MON_S") /* 진료시간(월요일)S */
    private String operHourMonS;
    @Column(name = "OPER_HOUR_TUE_S") /* 진료시간(화요일)S */
    private String operHourTueS;
    @Column(name = "OPER_HOUR_WED_S") /* 진료시간(수요일)S */
    private String operHourWedS;
    @Column(name = "OPER_HOUR_THU_S") /* 진료시간(목요일)S */
    private String operHourThuS;
    @Column(name = "OPER_HOUR_FRI_S") /* 진료시간(금요일)S */
    private String operHourFriS;
    @Column(name = "OPER_HOUR_SAT_S") /* 진료시간(토요일)S */
    private String operHourSatS;
    @Column(name = "OPER_HOUR_SUN_S") /* 진료시간(일요일)S */
    private String operHourSunS;
    @Column(name = "OPER_HOUR_HOL_S") /* 진료시간(공휴일)S */
    private String operHourHolS;

    @Column(name = "ZIP_CODE1") /* 우편번호1 */
    private String zipCode1;
    @Column(name = "ZIP_CODE2") /* 우편번호2 */
    private String zipCode2;

    @Column(name = "LONGITUDE") /* 병원경도 */
    private Double lon;
    @Column(name = "LATITUDE") /* 병원위도 */
    private Double lat;

    @Column(name = "DATE") /* 작업시간 */
    private String date;
}
