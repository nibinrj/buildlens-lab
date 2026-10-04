# buildlens-lab

A small multi-module Maven project that [BuildLens](https://github.com/nibinrj/buildlens) uses as its build
subject. Jenkins builds it, and every build is reported to BuildLens: test results, stage timings and the log
tail.

| Module | Contents |
| --- | --- |
| `core` | Plain code with stable tests |
| `api` | Depends on `core`. A **controlled** slow test (duration raised by a system property) arrives in BuildLens step P4 |
| `flaky-lab` | Home of the **injected** flaky tests, which arrive in BuildLens step P3. Empty for now |

## Injected flakiness

**None yet.** Every test in this repository is currently meant to pass on every build. When injected flaky tests
are added (P3), this section will list each one: what makes it fail (random, time-based or order-dependent), how
often, and the seed that makes it reproducible. Any flakiness BuildLens reports in `core` or `api` is not injected.

## Build

JDK 21 and Maven 3.9:

```
mvn -B verify
```

Surefire reruns a failing test up to twice (`surefire.rerunFailingTestsCount=2` in the parent POM). A test that
passes on a rerun is reported as a flake, which BuildLens's rule R1 uses.

The `Jenkinsfile` uses the `buildlens` shared library (`timedStage`, `reportBuild`) and runs on the agent labelled
`maven` in the BuildLens local Jenkins.
