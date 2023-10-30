package org.zerock.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.domain.MemberVO;
import org.zerock.myapp.domain.Role;
import org.zerock.myapp.mapper.AdminMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private int perPage = 5;

    @Override
    public Integer offset(Integer pageNum){ // 페이징 처리를 통해 해당되는 레코드를 출력
        return (pageNum - 1) * perPage;
    }

    @Override
    public List<MemberVO> findAllMember(Integer pageNum) { // 전체 회원 조회
        log.trace("findAllMember({}) invoked.", pageNum);

        Integer offset = offset(pageNum);
        log.info("\t+ offset: {}, perPage: {}", offset, perPage);

        return this.adminMapper.selectAllMember(offset, perPage);
    }

    @Override
    public List<MemberVO> searchMemberNameOrEmail(Integer pageNum, String searchOption, String searchValue) { // 검색기능 (이름, 이메일)
        log.trace("searchMemberNameOrEmail({}, {}) invoked", searchOption, searchValue);

        int offset = offset(pageNum);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("searchOption", searchOption);
        parameters.put("searchValue", searchValue);

        return this.adminMapper.selectMemberNameOrEmail(parameters, offset, perPage);
    }

    @Override
    public MemberVO findDetailMember(Long id) { // 회원 상세정보 서비스
        log.trace("findDetailMember({}) invoked", id);

        MemberDTO dto = new MemberDTO();
        dto.setId(id);

        return this.adminMapper.selectDetailMember(dto);
    }

    @Override
    public Integer joinMember(MemberDTO dto) { // 회원가입 서비스
        log.trace("joinMember({}) invoked", dto);

        return this.adminMapper.insertMember(dto);
    }

    @Override
    public Integer editMember(Long id, String nickname, String address, String[] phoneNum, String role) { // 회원수정 서비스
        log.trace("editMember({}, {}, {}, {}, {}) invoked", id, nickname, address, Arrays.toString(phoneNum), role);

        MemberDTO dto = new MemberDTO();
        dto.setId(id);
        dto.setNickname(nickname);
        dto.setAddress(address);
        dto.setPhoneNum(phoneNum[0]+"-"+phoneNum[1]+"-"+phoneNum[2]);
        if(role.equals("ADMIN")) {
            dto.setRole(Role.ROLE_ADMIN);
        }else{
            dto.setRole(Role.ROLE_MEMBER);
        }

        return this.adminMapper.updateMember(dto);
    }

    @Override
    public Integer deleteMember(Long[] selectedMembers) { // 회원삭제 서비스
        log.trace("deleteMember({}) invoked", Arrays.toString(selectedMembers));

        MemberDTO dto = new MemberDTO();
        for(Long selectedMember : selectedMembers) {
            log.info("\t+ selectedMember : {}", selectedMember);
            dto.setId(selectedMember);
        }
        return this.adminMapper.deleteMember(dto);
    }

    @Override
    public Integer totalPage() {
        log.trace("totalMemberCount() invoked");
        int totalPage = this.adminMapper.totalMemberCount()/perPage;
        if(this.adminMapper.totalMemberCount() % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }

    @Override
    public Integer totalSearchPage(String searchOption, String searchValue) {
        log.trace("totalSearchPage({}, {})", searchOption, searchValue);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("searchOption", searchOption);
        parameters.put("searchValue", searchValue);

        Integer totalPage = this.adminMapper.selectTotalSearchCount(parameters)/perPage;

        if(this.adminMapper.selectTotalSearchCount(parameters) % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }


} // end class
