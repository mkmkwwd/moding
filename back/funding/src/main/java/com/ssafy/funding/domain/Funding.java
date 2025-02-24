package com.ssafy.funding.domain;

import static com.ssafy.funding.exception.global.CustomExceptionStatus.FUNDING_QUANTITY_LACK;

import com.ssafy.funding.exception.BadRequestException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Funding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_id")
    private Integer id;

    private LocalDateTime startAt;

    private LocalDate date;

    @Column(length = 5)
    private String time;

    private Integer peopleCount;

    private Integer price;

    /*
    한 영화는 한 상영관에서만 열리는 것으로 ERD가 되어있길래,,
     */
    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    /*
    이벤트 상태 필드
    ERD에서 없어서 한번 열렸다가 종료된 펀딩은 지울지 이야기 해봐야 함 -> movie에 status 있으니까 펀딩에서는 제거
     */
    //    @Enumerated(EnumType.STRING)
    //    private FundingStatus status;

    /** 펀딩 가능 시간이 아직 안지났는지. */
    public void validFundingTime(Funding funding) {
        funding.validateFundingTime();
    }

    public boolean validateFundingTime() {
        if (isTimeBeforeStartAt() || isTimeAfterEndAt()) return false;
        // throw FundingTimeIsPassedException.EXCEPTION;
        return true;
    }

    public void validateNotOpenStatus() {
        //        if (status != OPEN) throw FundingNotOpenException.EXCEPTION;
    }

    public boolean isTimeBeforeStartAt() {
        return LocalDateTime.now().isBefore(getStartAt());
    }

    public boolean isTimeAfterEndAt() {
        return LocalDateTime.now().isAfter(getEndAt());
    }

    //    public LocalDateTime getStartAt() {
    //        if (this.startAt == null) return null;
    //        return this.getStartAt();
    //    }

    public LocalDateTime getEndAt() {
        if (this.startAt == null) return null;
        return this.getStartAt().plusDays(13); // 펀딩은 2주동안 진행(시작일자 포함)
    }

    /*
    (전체 펀딩 모집 인원 - 펀딩에 실제 참여한 인원 수) < 현재 유저가 예매하려는 펀딩 수
     */
    public void validEnoughQuantity(Integer currentTotalOrderCnt, Integer requestCount) {
        if ((this.peopleCount - currentTotalOrderCnt) < requestCount) {
            throw new BadRequestException(FUNDING_QUANTITY_LACK);
            // throw FundingQuantityLackException.EXCEPTION;
        }
    }
}
