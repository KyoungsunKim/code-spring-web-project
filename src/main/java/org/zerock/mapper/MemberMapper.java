package org.zerock.mapper;

import org.zerock.domain.MemberVO;

public interface MemberMapper {

    MemberVO read(String userid);
}
