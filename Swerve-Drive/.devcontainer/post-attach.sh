#!/bin/bash
set -e

# Install the WPILib VS Code extension for GitHub Codespaces.
# WPILib is not on the VS Code marketplace, so customizations.vscode.extensions can't be
# used. postCreateCommand runs before VS Code Server is ready so `code --install-extension`
# fails there. postAttachCommand runs after VS Code has attached, at which point the code
# CLI is live and can register the extension with the running server.
# The Dockerfile handles local devcontainers (VSIX pre-extracted into the image).
if [ "${CODESPACES}" = "true" ]; then
  # Always install via the CLI so VS Code properly registers the extension and
  # activates it without requiring a manual reload. The Dockerfile pre-extracts
  # the files, but VS Code needs a CLI install to update its extension registry;
  # without it the extension sits on disk but never activates, breaking IntelliSense.
  echo "Installing WPILib VS Code extension..."
  wget -q 'https://github.com/wpilibsuite/vscode-wpilib/releases/download/v2026.2.1/vscode-wpilib-2026.2.1.vsix' -O /tmp/vscode-wpilib.vsix
  code --install-extension /tmp/vscode-wpilib.vsix --force
  rm /tmp/vscode-wpilib.vsix
fi
