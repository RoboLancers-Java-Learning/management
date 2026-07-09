#!/bin/bash
# Unit 1: Checks that the deploy/swerve JSON config files were filled in
# to match the spec sheet in unit1-swerve-config.md.

CONFIG_DIR="src/main/deploy/swerve"
PASS=1

check_value() {
  local file="$1"
  local py_path="$2"
  local expected="$3"
  local label="$4"

  # Compares numerically (so 12 and 12.0 are treated as equal) via Python,
  # since a fill-in-the-blank JSON value may be typed as an int or a float.
  result=$(python3 -c "
import json
with open('$file') as f:
    d = json.load(f)
actual = d$py_path
expected = $expected
ok = abs(float(actual) - float(expected)) < 1e-6
print(f'{ok}|{actual}')
" 2>/dev/null)

  ok="${result%%|*}"
  actual="${result#*|}"

  if [ "$ok" == "True" ]; then
    echo "PASS: $label ($file$py_path == $expected)"
  else
    echo "FAIL: $label ($file$py_path is '$actual', expected '$expected')"
    PASS=0
  fi
}

for f in swervedrive.json controllerproperties.json modules/frontleft.json modules/frontright.json modules/backleft.json modules/backright.json modules/physicalproperties.json modules/pidfproperties.json; do
  if ! python3 -m json.tool "$CONFIG_DIR/$f" > /dev/null 2>&1; then
    echo "FAIL: $CONFIG_DIR/$f is not valid JSON"
    exit 1
  fi
done

check_value "$CONFIG_DIR/swervedrive.json" "['imu']['id']" "13" "IMU CAN ID"

check_value "$CONFIG_DIR/modules/frontleft.json" "['drive']['id']" "1" "Front-left drive motor CAN ID"
check_value "$CONFIG_DIR/modules/frontleft.json" "['angle']['id']" "2" "Front-left angle motor CAN ID"
check_value "$CONFIG_DIR/modules/frontleft.json" "['encoder']['id']" "9" "Front-left CANcoder CAN ID"
check_value "$CONFIG_DIR/modules/frontleft.json" "['absoluteEncoderOffset']" "-63.4" "Front-left absolute encoder offset"
check_value "$CONFIG_DIR/modules/frontleft.json" "['location']['front']" "12.0" "Front-left location.front"
check_value "$CONFIG_DIR/modules/frontleft.json" "['location']['left']" "12.0" "Front-left location.left"

check_value "$CONFIG_DIR/modules/frontright.json" "['drive']['id']" "3" "Front-right drive motor CAN ID"
check_value "$CONFIG_DIR/modules/frontright.json" "['angle']['id']" "4" "Front-right angle motor CAN ID"
check_value "$CONFIG_DIR/modules/frontright.json" "['encoder']['id']" "10" "Front-right CANcoder CAN ID"
check_value "$CONFIG_DIR/modules/frontright.json" "['absoluteEncoderOffset']" "12.3" "Front-right absolute encoder offset"
check_value "$CONFIG_DIR/modules/frontright.json" "['location']['front']" "12.0" "Front-right location.front"
check_value "$CONFIG_DIR/modules/frontright.json" "['location']['left']" "-12.0" "Front-right location.left"

check_value "$CONFIG_DIR/modules/backleft.json" "['drive']['id']" "5" "Back-left drive motor CAN ID"
check_value "$CONFIG_DIR/modules/backleft.json" "['angle']['id']" "6" "Back-left angle motor CAN ID"
check_value "$CONFIG_DIR/modules/backleft.json" "['encoder']['id']" "11" "Back-left CANcoder CAN ID"
check_value "$CONFIG_DIR/modules/backleft.json" "['absoluteEncoderOffset']" "171.9" "Back-left absolute encoder offset"
check_value "$CONFIG_DIR/modules/backleft.json" "['location']['front']" "-12.0" "Back-left location.front"
check_value "$CONFIG_DIR/modules/backleft.json" "['location']['left']" "12.0" "Back-left location.left"

check_value "$CONFIG_DIR/modules/backright.json" "['drive']['id']" "7" "Back-right drive motor CAN ID"
check_value "$CONFIG_DIR/modules/backright.json" "['angle']['id']" "8" "Back-right angle motor CAN ID"
check_value "$CONFIG_DIR/modules/backright.json" "['encoder']['id']" "12" "Back-right CANcoder CAN ID"
check_value "$CONFIG_DIR/modules/backright.json" "['absoluteEncoderOffset']" "-100.2" "Back-right absolute encoder offset"
check_value "$CONFIG_DIR/modules/backright.json" "['location']['front']" "-12.0" "Back-right location.front"
check_value "$CONFIG_DIR/modules/backright.json" "['location']['left']" "-12.0" "Back-right location.left"

check_value "$CONFIG_DIR/modules/physicalproperties.json" "['conversionFactors']['drive']['diameter']" "4" "Wheel diameter (inches)"
check_value "$CONFIG_DIR/modules/physicalproperties.json" "['conversionFactors']['drive']['gearRatio']" "6.75" "Drive gear ratio"
check_value "$CONFIG_DIR/modules/physicalproperties.json" "['conversionFactors']['angle']['gearRatio']" "12.8" "Angle gear ratio"

if [ $PASS -eq 1 ]; then
  echo ""
  echo "All Unit 1 swerve config checks passed."
  exit 0
else
  echo ""
  echo "One or more swerve config values don't match the spec sheet. Review unit1-swerve-config.md and fix them."
  exit 1
fi
