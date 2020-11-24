package com.annakhuseinova.apiclient;

import com.annakhuseinova.domain.github.GitHubPosition;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.annakhuseinova.util.CommonUtil.startTimer;
import static com.annakhuseinova.util.CommonUtil.timeTaken;

public class GitHubJobsClient {

    private WebClient webClient;

    public GitHubJobsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<GitHubPosition> invokeGitHubJobsApi_withPageNumber(int pageNumber, String description){
        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", pageNumber)
                .buildAndExpand()
                .toUriString();
        List<GitHubPosition> gitHubPositions = webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPosition.class)
                .collectList()
                .block();
        return gitHubPositions;
    }

    public List<GitHubPosition> invokeGithubJobsAPI_usingMultiplePageNumbers(List<Integer> pageNumbers,
                                                                             String description){
        startTimer();
        List<GitHubPosition> gitHubPositions = pageNumbers.stream()
                .map(pageNumber -> invokeGitHubJobsApi_withPageNumber(pageNumber, description))
                .flatMap(Collection::stream).collect(Collectors.toList());
        timeTaken();
        return gitHubPositions;
    }

    public List<GitHubPosition> invokeGithubJobsAPI_usingMultiplePageNumbersAndCF(List<Integer> pageNumbers,
                                                                             String description){
        startTimer();
        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageNumbers.stream()
                .map(pageNumber -> CompletableFuture.supplyAsync((()->
                        invokeGitHubJobsApi_withPageNumber(pageNumber, description)))).collect(Collectors.toList());
        List<GitHubPosition> gitHubPositions1 =
                gitHubPositions.stream().map(CompletableFuture::join).flatMap(Collection::stream).collect(Collectors.toList());
        timeTaken();
        return gitHubPositions1;
    }


}
