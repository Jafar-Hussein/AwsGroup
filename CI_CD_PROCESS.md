## CI/CD Pipeline

This project uses GitHub Actions to automate the build and test process. Each time a change is pushed to the repo, a series of tests are run to ensure that the code still works as expected. If all tests pass, the project is built and a JAR file is generated.

This JAR file can then be deployed to AWS to make the application available online.

## CI/CD Process

The CI/CD process for this project involves pushing to the GitHub repository, triggering a GitHub Actions workflow, and deploying through an AWS pipeline. Here's a step-by-step breakdown:

1. **Push to Repository**: Changes are made to the code and pushed to the GitHub repository.

2. **Trigger GitHub Actions**: The push to the repository triggers the GitHub Actions workflow defined in the `.github/workflows/Maven.yml` file. This workflow sets up the environment, builds the project with Maven, and runs tests.

![Github Actions](GithubActions.png)

3. **AWS Pipeline**: After the GitHub Actions workflow completes, the AWS pipeline is triggered. The pipeline consists of three stages:

    - **Source**: The pipeline pulls the latest version of the code from the GitHub repository.

    - **Build**: The code is compiled, tests are run, and the application is packaged into a deployable format like a JAR file.

    - **Deploy**: The packaged application is deployed to a server or a service on AWS.

![AWS Pipeline](AWSPipeline.png)

This process ensures a streamlined development and deployment process, with every change to the code going through a series of automated tests and being automatically deployed to AWS.

