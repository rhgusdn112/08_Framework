


// ---------------------------------------------------------
let selectChattingNo; // 선택한 채팅방 번호
let selectTargetNo; // 현재 채팅 대상
let selectTargetName; // 대상의 이름
let selectTargetProfile; // 대상의 프로필


/* 사용자 검색 팝업 레이어 열기 */
const addTarget = document.querySelector("#addTarget"); // 추가 버튼

const addTargetPopupLayer
  = document.querySelector("#addTargetPopupLayer"); // 팝업 레이어

const closeBtn = document.querySelector("#closeBtn"); // 팝업 닫기

const targetInput = document.querySelector("#targetInput"); // 검색창

const resultArea = document.querySelector("#resultArea"); // 검색 결과 목록


// 추가 버튼 클릭 시
addTarget.addEventListener("click", () => {
  addTargetPopupLayer.classList.remove("popup-layer-close");
  targetInput.focus();
})

// 닫기(X) 버튼 클릭 시
closeBtn.addEventListener("click", () => {
  addTargetPopupLayer.classList.add("popup-layer-close");
  resultArea.innerHTML = ""; // 검색 목록 내용 지우기
})

// 사용자 검색(AJAX)
targetInput.addEventListener("input", () => {

  // 입력된 값
  const query = targetInput.value.trim();

  // 입력된 값이 없을 경우
  if (query.length === 0) {
    resultArea.innerHTML = ""; // 검색 결과 목록 삭제
    return;
  }

  // 입력된 값이 있을 경우
  fetch("/chatting/selectTarget?query=" + query)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("검색 실패");
    })
    .then(list => {

      console.log(list);

      resultArea.innerHTML = ""; // 이전 검색 결과 비우기

      if (list.length == 0) {
        const li = document.createElement("li");
        li.classList.add("result-row");
        li.innerText = "일치하는 회원이 없습니다";
        resultArea.append(li);
        return;
      }

      for (let member of list) {
        // li요소 생성(한 행을 감싸는 요소)
        const li = document.createElement("li");
        li.classList.add("result-row");
        li.setAttribute("data-id", member.memberNo);

        // 프로필 이미지 요소
        const img = document.createElement("img");
        img.classList.add("result-row-img");

        // 프로필 이미지 여부에 따른 src 속성 선택
        if (member.profileImage == null) img.setAttribute("src", userDefaultImage);
        else img.setAttribute("src", member.profileImage);

        let nickname = member.memberNickname;
        let email = member.memberEmail;

        const span = document.createElement("span");
        span.innerHTML = `${nickname} ${email}`.replace(query, `<mark>${query}</mark>`);

        // 요소 조립(화면에 추가)
        li.append(img, span);
        resultArea.append(li);

        // 클릭 시 채팅방 입장 함수 호출
        li.addEventListener("click", chattingEnter);

      }


    })
    .catch(err => console.error(err));

});


/**
 * 채팅방 입장
 * @param e : 이벤트 객체
 */
const chattingEnter = (e) => {

  // e.currentTarget : 이벤트 리스너가 설정된 요소

  // data-id 값을 얻어와 저장(참여자 회원 번호)
  const targetNo = e.currentTarget.dataset.id;
  // console.log(targetNo);

  fetch("/chatting/enter",{
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : targetNo
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("입장 실패");
  })
  .then(chattingNo => {
    // chattingNo : 입장한 채팅방 번호

    console.log(chattingNo);

  })
  .catch(err => console.error(err));

}


// -----------------------------------------------
// 비동기로 채팅방 목록 조회
const selectRoomList = () => {

	fetch("/chatting/roomList")
	.then(resp => resp.json())
	.then(roomList => {
		console.log(roomList);

		// 채팅방 목록 출력 영역 선택
		const chattingList = document.querySelector(".chatting-list");

		// 채팅방 목록 지우기
		chattingList.innerHTML = "";

		// 조회한 채팅방 목록을 화면에 추가
		for(let room of roomList){
			const li = document.createElement("li");
			li.classList.add("chatting-item");
			li.setAttribute("chat-no", room.chattingNo);
			li.setAttribute("target-no", room.targetNo);

			if(room.chattingNo == selectChattingNo){
				li.classList.add("select");
			}

			// item-header 부분
			const itemHeader = document.createElement("div");
			itemHeader.classList.add("item-header");

			const listProfile = document.createElement("img");
			listProfile.classList.add("list-profile");

			if(room.targetProfile == undefined)	
				listProfile.setAttribute("src", userDefaultImage);
			else								
				listProfile.setAttribute("src", room.targetProfile);

			itemHeader.append(listProfile);

			// item-body 부분
			const itemBody = document.createElement("div");
			itemBody.classList.add("item-body");

			const p = document.createElement("p");

			const targetName = document.createElement("span");
			targetName.classList.add("target-name");
			targetName.innerText = room.targetNickname;
			
			const recentSendTime = document.createElement("span");
			recentSendTime.classList.add("recent-send-time");
			recentSendTime.innerText = room.sendTime;
			
			
			p.append(targetName, recentSendTime);
			
			
			const div = document.createElement("div");
			
			const recentMessage = document.createElement("p");
			recentMessage.classList.add("recent-message");

			if(room.lastMessage != undefined){
				recentMessage.innerHTML = room.lastMessage;
			}
			
			div.append(recentMessage);

			itemBody.append(p,div);

			// 현재 채팅방을 보고있는게 아니고 읽지 않은 개수가 0개 이상인 경우 -> 읽지 않은 메세지 개수 출력
			if(room.notReadCount > 0 && room.chattingNo != selectChattingNo ){
				const notReadCount = document.createElement("p");
				notReadCount.classList.add("not-read-count");
				notReadCount.innerText = room.notReadCount;
				div.append(notReadCount);
			}else{

				// 현재 채팅방을 보고있는 경우
				// 비동기로 해당 채팅방 글을 읽음으로 표시
				fetch("/chatting/updateReadFlag",{
					method : "PUT",
					headers : {"Content-Type": "application/json"},
					body : JSON.stringify({"chattingNo" : selectChattingNo})
				})
				.then(resp => resp.text())
				.then(result => console.log(result))
				.catch(err => console.log(err));

			}
			

			li.append(itemHeader, itemBody);
			chattingList.append(li);
		}

		roomListAddEvent();
	})
	.catch(err => console.log(err));
}

// ------------------------------------

// 채팅방 목록에 이벤트를 추가하는 함수 
const roomListAddEvent = () => {
	const chattingItemList = document.getElementsByClassName("chatting-item");
	
	for(let item of chattingItemList){
		item.addEventListener("click", e => {
	
			// 전역변수에 채팅방 번호, 상대 번호, 상태 프로필, 상대 이름 저장
			selectChattingNo = item.getAttribute("chat-no");
			selectTargetNo = item.getAttribute("target-no");

			selectTargetProfile = item.children[0].children[0].getAttribute("src");
			selectTargetName = item.children[1].children[0].children[0].innerText;

			if(item.children[1].children[1].children[1] != undefined){
				item.children[1].children[1].children[1].remove();
			}
	
			// 모든 채팅방에서 select 클래스를 제거
			for(let it of chattingItemList) it.classList.remove("select")
	
			// 현재 클릭한 채팅방에 select 클래스 추가
			item.classList.add("select");
	
			// 비동기로 메세지 목록을 조회하는 함수 호출
			selectChattingFn();
		});
	}
}


// ---------------------------------
// 비동기로 메세지 목록을 조회하는 함수
const selectChattingFn = () => {

	fetch(`/chatting/selectMessage?chattingNo=${selectChattingNo}`)
	.then(resp => resp.json())
	.then(messageList => {
		console.log(messageList);

		// <ul class="display-chatting">
		const ul = document.querySelector(".display-chatting");

		ul.innerHTML = ""; // 이전 내용 지우기

		// 메세지 만들어서 출력하기
		for(let msg of messageList){
			//<li>,  <li class="my-chat">
			const li = document.createElement("li");

			// 보낸 시간
			const span = document.createElement("span");
			span.classList.add("chatDate");
			span.innerText = msg.sendTime;

			// 메세지 내용
			const p = document.createElement("p");
			p.classList.add("chat");
			p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML

			// 내가 작성한 메세지인 경우
			if(loginMemberNo == msg.senderNo){ 
				li.classList.add("my-chat");
				
				li.append(span, p);
				
			}else{ // 상대가 작성한 메세지인 경우
				li.classList.add("target-chat");

				// 상대 프로필
				const img = document.createElement("img");
				img.setAttribute("src", selectTargetProfile);
				
				const div = document.createElement("div");

				// 상대 이름
				const b = document.createElement("b");
				b.innerText = selectTargetName; // 전역변수

				const br = document.createElement("br");

				div.append(b, br, p, span);
				li.append(img,div);

			}

			ul.append(li);
			display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
		}

	})
	.catch(err => console.log(err));
}
