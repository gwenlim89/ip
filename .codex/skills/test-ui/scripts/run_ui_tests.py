#!/usr/bin/env python3
from __future__ import annotations

import subprocess
import sys
import tempfile
from dataclasses import dataclass
from pathlib import Path


@dataclass
class TestCase:
    name: str
    aim: str
    inputs: str
    expected_output: str


def main() -> int:
    repo_root = Path(__file__).resolve().parents[4]
    plan_path = repo_root / "test" / "ui-test-plan.md"

    if not plan_path.exists():
        print(f"Missing UI test plan: {plan_path}", file=sys.stderr)
        return 1

    test_cases = parse_test_plan(plan_path)
    if not test_cases:
        print(f"No test cases found in {plan_path}", file=sys.stderr)
        return 1

    java_files = sorted((repo_root / "src" / "main" / "java").glob("*.java"))
    if not java_files:
        print("No Java source files found under src/main/java", file=sys.stderr)
        return 1

    with tempfile.TemporaryDirectory(prefix="larper-ui-tests-") as build_dir:
        compile_command = ["javac", "-d", build_dir] + [str(path) for path in java_files]
        compile_result = subprocess.run(
            compile_command,
            cwd=repo_root,
            text=True,
            capture_output=True,
            check=False,
        )
        if compile_result.returncode != 0:
            print("Compilation failed.", file=sys.stderr)
            print(compile_result.stdout, end="")
            print(compile_result.stderr, end="", file=sys.stderr)
            return compile_result.returncode

        for index, test_case in enumerate(test_cases, start=1):
            actual_output = run_larper(build_dir, repo_root, test_case.inputs)
            expected_output = normalize_output(test_case.expected_output)
            normalized_actual = normalize_output(actual_output)

            if normalized_actual != expected_output:
                print_failure(index, test_case, normalized_actual, expected_output)
                return 1

            print_success(index, test_case, normalized_actual)

    print(f"All {len(test_cases)} UI test case(s) passed.")
    return 0


def parse_test_plan(plan_path: Path) -> list[TestCase]:
    lines = plan_path.read_text(encoding="utf-8").splitlines()
    sections: list[tuple[str, list[str]]] = []
    current_name: str | None = None
    current_lines: list[str] = []

    for line in lines:
        if line.startswith("## Test Case:"):
            if current_name is not None:
                sections.append((current_name, current_lines))
            current_name = line.split(":", 1)[1].strip()
            current_lines = []
        elif current_name is not None:
            current_lines.append(line)

    if current_name is not None:
        sections.append((current_name, current_lines))

    return [parse_section(name, section_lines) for name, section_lines in sections]


def parse_section(name: str, lines: list[str]) -> TestCase:
    aim = ""
    for line in lines:
        if line.startswith("Aim:"):
            aim = line.split(":", 1)[1].strip()
            break

    inputs = extract_fenced_block(lines, "Inputs:")
    expected_output = extract_fenced_block(lines, "Expected output:")

    if not aim:
        raise ValueError(f"Test case '{name}' is missing Aim.")
    if inputs is None:
        raise ValueError(f"Test case '{name}' is missing Inputs block.")
    if expected_output is None:
        raise ValueError(f"Test case '{name}' is missing Expected output block.")

    return TestCase(name=name, aim=aim, inputs=inputs, expected_output=expected_output)


def extract_fenced_block(lines: list[str], label: str) -> str | None:
    index = 0
    while index < len(lines):
        if lines[index].strip() == label:
            index += 1
            while index < len(lines) and not lines[index].startswith("```"):
                index += 1
            if index == len(lines):
                return None
            index += 1
            block_lines: list[str] = []
            while index < len(lines) and not lines[index].startswith("```"):
                block_lines.append(lines[index])
                index += 1
            return "\n".join(block_lines)
        index += 1
    return None


def run_larper(build_dir: str, repo_root: Path, console_input: str) -> str:
    input_text = console_input
    if not input_text.endswith("\n"):
        input_text += "\n"

    result = subprocess.run(
        ["java", "-Dlarper.today=2026-08-23", "-cp", build_dir, "Larper"],
        cwd=repo_root,
        input=input_text,
        text=True,
        capture_output=True,
        check=False,
    )
    return result.stdout + result.stderr


def normalize_output(output: str) -> str:
    return output.replace("\r\n", "\n").replace("\r", "\n").rstrip() + "\n"


def print_success(index: int, test_case: TestCase, actual_output: str) -> None:
    print(f"PASS {index}: {test_case.name}")
    print(f"Aim: {test_case.aim}")
    print("Console input:")
    print(fenced(test_case.inputs))
    print("Console output:")
    print(fenced(actual_output.rstrip("\n")))


def print_failure(index: int, test_case: TestCase, actual_output: str, expected_output: str) -> None:
    print(f"FAIL {index}: {test_case.name}")
    print(f"Aim: {test_case.aim}")
    print("Console input:")
    print(fenced(test_case.inputs))
    print("Expected output:")
    print(fenced(expected_output.rstrip("\n")))
    print("Actual output:")
    print(fenced(actual_output.rstrip("\n")))


def fenced(text: str) -> str:
    return "```text\n" + text + "\n```"


if __name__ == "__main__":
    raise SystemExit(main())
