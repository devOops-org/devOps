package kr.seok.elastic.domain;

/**
 * 엔티티와 구분되기 위한 DTO
 * - 파일로부터 읽어온 데이터를 Entity에 넣는 메서드
 */
public class HospitalDto {
    /* 병원 데이터 Dto -> Entity */
    public static HospitalEntity toEntity(String... split) {
        return HospitalEntity.builder()
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
                .build();
    }
}
