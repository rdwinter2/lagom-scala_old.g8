#!/usr/bin/env bash
toplevel=`git rev-parse --show-toplevel`
#repository_name=`basename `git rev-parse --show-toplevel``
#current_branch=`git rev-parse --abbrev-ref HEAD`
#present_working_directory=`pwd`

# create a link from custom-hooks to .git/hooks
ln -s \${toplevel}/custom-hooks/pre-commit \${toplevel}/.git/hooks/pre-commit
