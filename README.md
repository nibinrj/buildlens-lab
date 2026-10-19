# buildlens-lab

Maven build subject for [BuildLens](https://github.com/nibinrj/buildlens).
Versions reuse the P2 parent POM pins; use JDK 21 and the pinned Maven wrapper in BuildLens.

| Module | Contents |
| --- | --- |
| core | Stable calculator and slug tests |
| api | Stable price tests and an injected controlled duration test |
| flaky-lab | **Injected** random, time and order flakiness |

## Injected tests

These are demonstrations, never findings of naturally occurring flaky tests.

- RandomTest#sometimesFails uses a seeded SplittableRandom, comparing each draw to 0.20.
  The 20% is an injection policy per invocation, not a measured build failure rate.
  Surefire advances the PRNG on reruns.
- TimeTest#expiresAtBoundary uses Clock.fixed and a simulated five-second expiry boundary.
  Each retry advances simulated time by one second; host time and sleeps play no role.
- OrderTest#polluter leaks static fixture state. OrderTest#victim fails if it follows the polluter,
  then resets the fixture so a retry can pass. A seeded shuffle determines method order.

Reproduce with the same seed, simulated time, JDK and pinned Surefire/JUnit versions:

```text
mvn -B -ntp -Dbuildlens.flaky.seed=5 -Dbuildlens.flaky.clockMillis=5000 verify
```

Surefire reruns failures twice. A recovered failure appears in flakyFailure/flakyError XML.
The normal Jenkinsfile uses BUILD_NUMBER as seed and BUILD_NUMBER * 1000 as simulated time.

## Quarantine integration

quarantineAwareTests fetches one active snapshot. The blocking stage excludes those Class#method
patterns; the non-blocking Quarantine stage runs only those tests. An unavailable service gives
empty exclusions, so all tests run in the blocking stage. The parent POM maps buildlens.reportSuffix
to reportsDirectory; quarantine XML goes in target/surefire-reports-quarantine. Each upload
labels reports BLOCKING or QUARANTINE. Clean checkout prevents old XML being reported again.

A failed quarantined test marks its stage UNSTABLE while preserving the overall build result.
Quarantine-stage passes can release a test after the configured consecutive-pass threshold.

## Controlled duration regression

ControlledSlowTest#controlledWork adds an **injected** delay selected by
-Dbuildlens.slow.ms=0..10000 (milliseconds). The default is zero. It still checks
the price service result; the delay is demonstration input, not evidence of a
real performance defect. Invalid values fail rather than silently changing policy.

Use the same pinned runtime and baseline input, then raise the delay for two PR
builds, for example: mvn -B -ntp -pl api -am -Dbuildlens.slow.ms=3000 test.
The Jenkins SLOW_MILLIS parameter passes this property through the blocking and
quarantine test invocations. Capture agent allocation time immediately inside
node(); reportBuild sends its measured busy duration separately from build duration.
Local reported cost is equivalent compute cost at a dated reference price.