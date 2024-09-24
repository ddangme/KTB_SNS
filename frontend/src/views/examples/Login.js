import React, {useEffect, useRef, useState} from "react";
import { useNavigate } from "react-router-dom";

// reactstrap components
import {
  Button,
  Card,
  CardBody,
  FormGroup,
  Form,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  Container,
  Row,
  Col, Modal,
} from "reactstrap";

// core components
import DemoNavbar from "components/Navbars/DemoNavbar.js";
import SimpleFooter from "components/Footers/SimpleFooter.js";

import kakaoIcon from "assets/img/icons/common/kakao.png";
import {ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay} from "@chakra-ui/react";

export default function Login() {
  const navigate = useNavigate();
  const mainRef = useRef(null);

  useEffect(() => {
    // 메시지 이벤트 리스너 추가
    window.addEventListener('message', handleMessage);
    return () => window.removeEventListener('message', handleMessage);
  }, []);

  const handleMessage = (event) => {
    // 원하는 출처(origin)에서 온 메시지인지 확인
    if (event.origin !== "http://localhost:3000") return;

    if (event.data.type === 'KAKAO_LOGIN' && event.data.code) {
      console.log('Received Kakao auth code:', event.data.code);
      // 여기서 받은 코드로 추가 처리 (예: 백엔드로 전송)
    }
  };

  useEffect(() => {
    document.documentElement.scrollTop = 0;
    document.scrollingElement.scrollTop = 0;
    if (mainRef.current) {
      mainRef.current.scrollTop = 0;
    }
  }, []);

  const kakaoLoginAPI = () => {
    const width = 500;
    const height = 600;
    const left = window.screenX + (window.outerWidth - width) / 2;
    const top = window.screenY + (window.outerHeight - height) / 2;

    window.open(
        'https://kauth.kakao.com/oauth/authorize?client_id=ab3811d87c797a0cf6e5cab1064a4e2a&redirect_uri=http://localhost:3000/api/v1/login/oauth2/code/kakao&response_type=code',
        'KakaoLoginPopup',
        `width=${width},height=${height},left=${left},top=${top}`
    );
  };

  return (
      <>
        <DemoNavbar/>
        <main ref={mainRef}>
          <section className="section section-shaped section-lg">
            <div className="shape shape-style-1 bg-gradient-default">
              <span/>
              <span/>
              <span/>
              <span/>
              <span/>
              <span/>
              <span/>
              <span/>
            </div>
            <Container className="pt-lg-7">
              <Row className="justify-content-center">
                <Col lg="5">
                  <Card className="bg-secondary shadow border-0">
                    <CardBody className="px-lg-5 py-lg-5">
                      <Form role="form">
                        <FormGroup className="mb-3">
                          <InputGroup className="input-group-alternative">
                            <InputGroupAddon addonType="prepend">
                              <InputGroupText>
                                <i className="ni ni-email-83"/>
                              </InputGroupText>
                            </InputGroupAddon>
                            <Input placeholder="Email" type="email"/>
                          </InputGroup>
                        </FormGroup>
                        <FormGroup>
                          <InputGroup className="input-group-alternative">
                            <InputGroupAddon addonType="prepend">
                              <InputGroupText>
                                <i className="ni ni-lock-circle-open"/>
                              </InputGroupText>
                            </InputGroupAddon>
                            <Input
                                placeholder="Password"
                                type="password"
                                autoComplete="off"
                            />
                          </InputGroup>
                        </FormGroup>
                        <div className="custom-control custom-control-alternative custom-checkbox">
                          <input
                              className="custom-control-input"
                              id=" customCheckLogin"
                              type="checkbox"
                          />
                          <label
                              className="custom-control-label"
                              htmlFor=" customCheckLogin"
                          >
                            <span>Remember me</span>
                          </label>
                        </div>
                        <div className="text-center">
                          <Button
                              className="my-4"
                              color="primary"
                              type="button"
                          >
                            로그인
                          </Button>
                          <Button
                              style={{
                                backgroundColor: 'rgb(254, 229, 2)',
                                color: "black",
                                borderColor: 'rgb(254, 229, 2)'
                              }}
                              className="btn-neutral btn-icon"
                              onClick={kakaoLoginAPI}
                          >
                          <span className="btn-inner--icon mr-1">
                            <img
                                alt="카카오 로그인"
                                src={kakaoIcon}
                            />
                          </span>
                            <span className="btn-inner--text">카카오 로그인</span>
                          </Button>
                        </div>
                      </Form>
                    </CardBody>
                  </Card>
                  <Row className="mt-3">
                    <Col xs="6">

                      className="text-light"
                      href="#pablo"
                      onClick={(e) => e.preventDefault()}
                      >
                      <small>비밀번호 찾기</small>
                    </Col>
                    <Col className="text-right" xs="6">

                      className="text-light"
                      href="#pablo"
                      onClick={(e) => e.preventDefault()}
                      >
                      <small>회원가입</small>
                    </Col>
                  </Row>
                </Col>
              </Row>
            </Container>
          </section>
        </main>
        <SimpleFooter/>
      </>
  );
};