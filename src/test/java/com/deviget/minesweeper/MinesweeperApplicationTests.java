package com.deviget.minesweeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class MinesweeperApplicationTests {
	
	private MockMvc mockMvc;
	private final String sessionId = "f5120516-d960-49cc-94d7-bc59c338acb7";
	private final String gameId = "c12bd303-affa-40d9-9bdf-2a4d21eaa3c0";
	
	 @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

	@Test
	public void testNewGame() throws Exception {
		mockMvc.perform(post("/game/new/14/14/10")
	            .contentType("application/json").
	            header("sessionId", sessionId)).andDo(print())
	            .andExpect(status().isOk())
	            .andDo(document("{methodName}",
	                    preprocessRequest(prettyPrint()),
	                    preprocessResponse(prettyPrint())));
	}
	
	@Test
    public void testOpenGame() throws Exception {
		mockMvc.perform(get("/game/open/" + gameId)
                .contentType("application/json").
                header("sessionId", sessionId)).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
	}
	
	@Test
    public void testPauseGame() throws Exception {
		mockMvc.perform(put("/game/pause/" + gameId)
                .contentType("application/json").
                header("sessionId", sessionId)).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
	}
	
	@Test
    public void testGetAll() throws Exception {
		mockMvc.perform(get("/game/all")
                .contentType("application/json").
                header("sessionId", sessionId)).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
	}
	
	@Test
    public void testDigCell() throws Exception {
		mockMvc.perform(put("/game/dig/" + gameId)
                .contentType("application/json").
                header("sessionId", sessionId)).andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
	}
	
	@Test
    public void testFlagCell() throws Exception {
		mockMvc.perform(put("/game/flag/" + gameId + "/20")
                .contentType("application/json").
                header("sessionId", sessionId)).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
	}
}
