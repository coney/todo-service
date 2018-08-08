package com.thoughtworks.training.wukun.todoservice.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Slf4j
@Component
public class SpellChecker {

    private int failureCount = 0;

    private JLanguageTool languageTool = new JLanguageTool(new AmericanEnglish());

    @SneakyThrows
    private String check(String sentence) {
        StringBuffer buffer = new StringBuffer(sentence);
        List<RuleMatch> matches = languageTool.check(sentence);
        matches.forEach(match -> buffer.replace(
                match.getFromPos(),
                match.getToPos(),
                match.getSuggestedReplacements().get(0)));
        return buffer.toString();
    }

    private void mayFail() {
        if (failureCount >= 2) {
            failureCount = 0;
        } else {
            log.info("spell api error with failure count: {}", failureCount);
            failureCount++;
            throw new RuntimeException(String.format("spell api error"));
        }
    }

    public synchronized <T> void check(Collection<T> todos, Function<T, String> textReader, BiConsumer<T, String> suggestionWriter) {
        log.info("check spell with failure count: {}", failureCount);
        mayFail();
        todos.stream().forEach(
                entity -> suggestionWriter.accept(entity, check(textReader.apply(entity)))
        );
    }
}
