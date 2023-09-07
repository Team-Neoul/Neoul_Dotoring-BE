package com.theZ.dotoring.app.letter.controller;

import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.handler.mento.CreateLetterByMentoHandler;
import com.theZ.dotoring.app.letter.handler.mento.CreateMentoLetterByRoomHandler;
import com.theZ.dotoring.app.letter.handler.mento.GetMentoLetterByRoomHandler;
import com.theZ.dotoring.app.letter.handler.mento.GetRoomByMentoHandler;
import com.theZ.dotoring.app.room.dto.RoomResponseDTO;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import com.theZ.dotoring.exception.NotFoundLetterException;
import com.theZ.dotoring.exception.NotFoundRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 멘토 입장에서의 쪽지, 쪽지함 API를 클라이언트와 통신합니다.
 *
 * @author Kevin
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
public class LetterFromMentoController {

    private final CreateLetterByMentoHandler createLetterMember2MemberHandler;

    private final CreateMentoLetterByRoomHandler createLetterFromRoomHandler;

    private final GetRoomByMentoHandler getRoomsFromMemberHandler;

    private final GetMentoLetterByRoomHandler getLettersFromMemberHandler;

    /**
     * 쪽지 상세 페이지가 아닌 곳에서 쪽지를 보내는 메서드
     *
     * @param letterRequestDTO - 클라이언트가 보낸 메세지 내용
     * @param mentoId - 쪽지를 보낸 멘토 id
     * @param mentiId - 쪽지를 받을 멘티 id
     *
     * @return 성공시 HTTP 성공 반환
     */
    @PostMapping("api/mento/letter/out/{mentiId}/{mentoId}")
    public ApiResponse<ApiResponse.CustomBody<Void>> sendLetterWhereOut(@Valid @RequestBody LetterByMemberRequestDTO letterRequestDTO, @PathVariable("mentiId") Long mentiId, @PathVariable("mentoId") Long mentoId) {
        createLetterMember2MemberHandler.execute(letterRequestDTO, mentoId, mentiId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    /**
     * 쪽지 상세 페이지에서 쪽지를 보내는 메서드
     *
     * @param letterRequestDTO - 클라이언트가 보낸 메세지 내용
     * @param mentoId - 쪽지를 보낸 멘토 id
     * @param roomPK  - 쪽지를 보낼 쪽지함
     *
     * @return 성공시 HTTP 성공 반환
     */
    @PostMapping("api/mento/letter/in/{roomPK}/{mentoId}")
    public void sendLetterWhereIn(@Valid @RequestBody LetterByMemberRequestDTO letterRequestDTO, @PathVariable("roomPK") Long roomPK, @PathVariable("mentoId") Long mentoId) {
        // mentoId : 멘토인 내 아이디 -> 시큐리티 도입과 함께 추후 삭제 되어야 함.
        createLetterFromRoomHandler.execute(letterRequestDTO, mentoId, roomPK);
    }

    /**
     * 쪽지를 보냈던 모든 쪽지함을 요청하는 메서드
     *
     * @param mentoId - API를 요청한 유저 id
     *
     * @return 성공시 HTTP 성공 반환
     */
    @GetMapping("api/mento/room/{mentoId}")
    public ApiResponse<ApiResponse.CustomBody<List<RoomResponseDTO>>> getRooms(@PathVariable("mentoId") Long mentoId) throws NotFoundRoomException {
        // mentoId : 멘토인 내 아이디 -> 시큐리티 도입과 함께 추후 삭제 되어야 함.
        return ApiResponseGenerator.success(getRoomsFromMemberHandler.execute(mentoId), HttpStatus.OK);
    }

    /**
     * 해당 쪽지함에서 주고 받은 모든 쪽지를 요청하는 메서드
     *
     * @param page - 페이지네이션을 위한 페이지
     * @param size - 페이지네이션을 위한 크기
     * @param roomPK - 쪽지를 보낸 쪽지 함
     * @param mentoId - API를 요청한 유저 id
     * @exception NotFoundLetterException - 쪽지가 존재하지 않는다면 발생하는 예외
     *
     * @return 성공시 HTTP 성공 반환
     */
    @GetMapping("api/mento/letter/{roomPK}/{mentoId}")
    public ApiResponse<ApiResponse.CustomBody<Slice<LetterByMemberResponseDTO>>> getLetters(@RequestParam int page, @RequestParam int size,
                                                       @PathVariable Long roomPK, @PathVariable("mentoId") Long mentoId) throws NotFoundLetterException {
        return ApiResponseGenerator.success(getLettersFromMemberHandler.execute(page, size, roomPK, mentoId), HttpStatus.OK);
    }
}