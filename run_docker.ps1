Set-Location $PSScriptRoot

docker compose -f compose.yaml up -d --build --force-recreate

Write-Host "Script completed."