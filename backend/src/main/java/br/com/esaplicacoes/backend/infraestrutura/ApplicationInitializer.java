package br.com.esaplicacoes.backend.infraestrutura;

import jakarta.annotation.PostConstruct;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationInitializer {

    private List<InitialTask> tasks;

    /**
     *
     *
     * @param tasks
     */
    public ApplicationInitializer(final List<InitialTask> tasks) {
        this.tasks = tasks;
    }

    /**
     *
     */
    @PostConstruct
    public void orderTasks() {
        this.tasks.sort(AnnotationAwareOrderComparator.INSTANCE);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onApplicationStart(final ContextRefreshedEvent event) {
        this.tasks.forEach(InitialTask::perform);
    }
}