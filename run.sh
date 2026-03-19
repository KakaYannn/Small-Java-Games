#!/usr/bin/env bash
set -euo pipefail

USAGE="Usage: $0 {pacman|maze-hunt|echoplay} [--clean]"

if [[ $# -lt 1 ]]; then
  echo "$USAGE"
  exit 1
fi

mode="$1"
shift

clean=false
if [[ "${1-}" == "--clean" ]]; then
  clean=true
fi

run_project() {
  local project_dir="$1"
  local main_class="$2"

  pushd "$project_dir" > /dev/null

  if [[ "$clean" == true ]]; then
    rm -rf out
  fi

  mkdir -p out
  javac -d out -sourcepath src $(find src -name '*.java')
  java -cp out "$main_class"

  popd > /dev/null
}

case "$mode" in
  pacman)
    run_project "Pacman Maze Game" GameEngine
    ;;
  maze-hunt)
    run_project "Maze Hunt Pacman" GameEngine
    ;;
  echoplay)
    run_project "EchoPlay" MusifyApp
    ;;
  *)
    echo "$USAGE"
    exit 1
    ;;
esac
