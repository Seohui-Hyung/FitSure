<template>
    <div class="notice-form-page">
      <h3 class="page-title">NOTICE FORM</h3>
      <hr />
      <!-- 제목 입력 -->
      <div class="title-container">
        <input
          v-model="state.title"
          type="text"
          class="title-input"
          placeholder="제목을 입력해주세요"
        />
      </div>
      <!-- 에디터 -->
      <div class="editor-container">
        <div ref="quillEditorContainer">
          <QuillEditor
            :options="state.editorOption"
            @error="handleEditorError"
          />
        </div>
      </div>
      <!-- 제출 버튼 -->
      <div class="button-container">
        <button class="submit-button" @click="submit">
          올리기
        </button>
      </div>
    </div>
  </template>
  
  <script>
  import { reactive, ref, onMounted } from "vue";
  import { useAdminStore } from "@/store/useAdminStore";
  
  export default {
    name: "NoticeForm",
    setup() {
      const adminStore = useAdminStore();
      const quillEditorContainer = ref(null); // Quill 에디터 컨테이너 참조
  
      const state = reactive({
        title: "",
        content: "",
        editorOption: {
          placeholder: "내용을 입력해주세요...",
          modules: {
            toolbar: [
              ["bold", "italic", "underline", "strike"],
              ["blockquote", "code-block"],
              [{ list: "ordered" }, { list: "bullet" }],
              [{ indent: "-1" }, { indent: "+1" }],
              [{ size: ["small", false, "large", "huge"] }],
              [{ header: [1, 2, 3, 4, 5, 6, false] }],
              [{ color: [] }, { background: [] }],
              [{ font: [] }],
              [{ align: [] }],
              ["clean"],
            ],
          },
        },
      });
  
      const handleEditorError = (error) => {
        console.error("QuillEditor Error:", error);
        alert("에디터 오류가 발생했습니다. 다시 시도해주세요.");
      };
  
      const submit = async () => {
        const { title } = state;
  
        // QuillEditor에서 HTML 콘텐츠 가져오기
        if (quillEditorContainer.value) {
          try {
            // DOM 내부에서 에디터 콘텐츠를 찾습니다.
            const editorRoot = quillEditorContainer.value.querySelector(".ql-editor");
            if (editorRoot) {
              state.content = editorRoot.innerHTML; // 에디터의 HTML 내용 가져오기
            } else {
              throw new Error("Failed to find .ql-editor in QuillEditor.");
            }
          } catch (error) {
            console.error("Failed to retrieve Quill instance:", error);
            alert("에디터에서 내용을 가져오는 데 실패했습니다.");
            return;
          }
        } else {
          console.error("QuillEditor container is null.");
          alert("에디터가 초기화되지 않았습니다.");
          return;
        }
  
        const { content } = state;
  
        if (!title || !content) {
          alert("제목과 내용을 모두 입력해주세요!");
          return;
        }
  
        console.log("content의 값:", content);
        try {
          adminStore.insertNotice({ title }, content);
          alert("공지사항이 등록되었습니다.");
        } catch (error) {
          console.error("Error submitting notice:", error);
          alert("공지 등록에 실패했습니다.");
        }
      };
  
      return {
        state,
        handleEditorError,
        submit,
        quillEditorContainer,
      };
    },
  };
  </script>
  
  <style>
  .notice-form-page {
    padding: 20px;
    background-color: #fcfcfc;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    max-width: 1800px;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
  }
  
  .page-title {
    margin-bottom: 20px;
    color: #000000;
    text-align: left !important;
  }
  
  .title-container {
    margin-bottom: 20px;
  }
  
  .title-input {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border: 2px solid #ddd;
    border-radius: 8px;
    box-sizing: border-box;
    outline: none;
  }
  
  .title-input:focus {
    border-color: #043873;
  }
  
  .editor-container {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
  height: 500px;
  display: flex;
  flex-direction: column; 
}

.ql-container {
  flex: 1; 
  border: 2px solid #ddd; 
  box-sizing: border-box; 
}
  
.ql-editor {
  padding: 10px;
  font-size: 16px;
  line-height: 1.6;
  height: 100%; 
}
  .button-container {
    text-align: right;
  }
  
  .submit-button {
    border: none;
    border-radius: 7px;
    width: 120px;
    height: 40px;
    font-size: 16px;
    background-color: #043873;
    color: #ffffff;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .submit-button:hover {
    background-color: #0056b3;
  }
  </style>