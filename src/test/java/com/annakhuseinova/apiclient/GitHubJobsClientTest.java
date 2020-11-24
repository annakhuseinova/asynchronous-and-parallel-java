package com.annakhuseinova.apiclient;

import com.annakhuseinova.domain.github.GitHubPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitHubJobsClientTest {

    private WebClient webClient = WebClient.create("https://jobs.github.com/");
    private GitHubJobsClient gitHubJobsClient = new GitHubJobsClient(webClient);

    @Test
    void invokeGitHubJobsApi_withPageNumber() {
        int pageNumber = 1;
        String description = "JavaScript";
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsApi_withPageNumber(pageNumber, description);
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions.forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGitHubJobsApi_withMultiplePageNumbers() {
        List<Integer> pageNumbers = List.of(1,2,3);
        String description = "JavaScript";
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGithubJobsAPI_usingMultiplePageNumbers(pageNumbers,
                description);
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions.forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGitHubJobsApi_withMultiplePageNumbersCF() {
        List<Integer> pageNumbers = List.of(1,2,3);
        String description = "JavaScript";
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGithubJobsAPI_usingMultiplePageNumbersAndCF(pageNumbers,
                description);
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions.forEach(Assertions::assertNotNull);
    }
}