package com.library.service;

import com.library.model.Member;
import com.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member registerMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new RuntimeException("Member with email " + member.getEmail() + " already exists");
        }
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member updated) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setStatus(updated.getStatus());
        return memberRepository.save(existing);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public List<Member> searchMembers(String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Member> getActiveMembers() {
        return memberRepository.findByStatus(Member.MemberStatus.ACTIVE);
    }

    public long countActiveMembers() {
        return memberRepository.findByStatus(Member.MemberStatus.ACTIVE).size();
    }

    public long countTotalMembers() {
        return memberRepository.count();
    }
}
