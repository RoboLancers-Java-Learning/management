Context
The WPILib extension installation in Codespaces has one remaining problem: postCreateCommand runs while VS Code Server is already running, so extension files placed on disk aren't in the server's activation-event index. The user sees a "Reload Required" notification instead of the extension activating cleanly on first open.

Codespaces prebuilds solve this by running postCreateCommand ahead of time — before any user connects — and snapshotting the resulting container state. When a user creates a codespace, they start from that snapshot. VS Code Server then starts fresh into a container where ~/.vscode-server/extensions/wpilibsuite.vscode-wpilib-2026.2.1/ already exists, scans it on startup, and activates the extension without a reload.

Critically, CODESPACES=true is set during prebuild execution, so the existing if [ "${CODESPACES}" = "true" ] guard in post-create.sh already handles this correctly — no code changes are required.

What Changes
No code changes to the devcontainer
The current setup is already correct for prebuilds:

post-create.sh extracts the VSIX under the $CODESPACES=true guard → runs during prebuild → files land in snapshot
Gradle JARs, WPILib tools, JDK symlink, and settings.json are all baked in during prebuild
The Dockerfile layer handles local Linux devcontainers independently
GitHub repository settings (one-time UI action)
Prebuilds are configured through GitHub's UI, not a workflow file:

Go to Settings → Codespaces
Click "Set up prebuild"
Configure:
Branch: main
Dev container config: .devcontainer/devcontainer.json
Prebuild triggers: On every push (plus optionally a weekly schedule as a fallback)
Region: select whichever region(s) users connect from
Save
GitHub creates and manages the underlying Actions workflow automatically; no .github/workflows/ file is needed.

Files Involved
File	Change
.devcontainer/devcontainer.json	None required
.devcontainer/post-create.sh	None required
.devcontainer/Dockerfile	None required
GitHub repository settings	Enable prebuild for main (UI action)
How the Flow Changes After Enabling Prebuilds
Before (no prebuild):

User creates codespace → container starts → VS Code Server starts → postCreateCommand runs (VSIX extracted, needs reload) → user connects → reload required
After (with prebuild):

Prebuild triggered on push to main → postCreateCommand runs with CODESPACES=true → VSIX extracted, tools installed, Gradle cache warm → snapshot saved
User creates codespace → starts from snapshot → VS Code Server starts, finds extension already in ~/.vscode-server/extensions/ → user connects → extension already active, no reload
Verification
Push a commit to main — confirm a prebuild job appears in Settings → Codespaces → Prebuilds and completes successfully
Create a new codespace (not resume) on main — startup should be noticeably faster (~30–60 s vs several minutes)
Open the codespace in the browser — WPILib status bar item and activity bar icon should be present immediately without a "Reload Required" notification
Run WPILib: Set Team Number from the command palette to confirm the extension is fully activated
Confirm Java IntelliSense resolves WPILib types in src/main/java/