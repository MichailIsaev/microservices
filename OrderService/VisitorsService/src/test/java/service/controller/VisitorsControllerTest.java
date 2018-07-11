package service.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.configuration.DataConfig;
import service.controllers.VisitorsController;
import service.domain.Visitors;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VisitorsController.class)
@ContextConfiguration(
        classes = {DataConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class VisitorsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    @MockBean
    private Visitors visitors;

    @Autowired
    @MockBean
    private VisitorsController visitorsController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(VisitorsController.class)
                .build();
    }

    @Test
    public void getVisitorByIdTest() throws Exception {
        visitors.getVisitors().forEach(
                consumer -> {
                    assertTrue(ResponseEntity.ok(consumer).equals(visitorsController.getVisitorById(consumer.getId())));
                    when(visitorsController.getVisitorById(consumer.getId())).thenReturn(ResponseEntity.ok(consumer));
                    try {
                        mockMvc.perform(get(String.format("/visitors/%s", consumer.getId()))).andExpect(status().isOk());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }
}
