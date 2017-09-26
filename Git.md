# Git Guide

[TOC]

## 使用GitHub

- 在GitHub上Fork任意开源仓库，自己拥有Fork后的仓库的读写权限。

- clone Fork后的仓库，进行修改，然后push到自己Fork的仓库。之后就可以 pull request 到开源仓库。

## 远程仓库

- 创建SSH

```bash
ssh-keygen -t rsa -C "youremail@example.com"
```

Shell或者Git Bash执行以上命令，一路默认回车。执行完以上命令后会在用户主目录下生成 .ssh 文件夹，和 id_rsa和id_rsa.pub这两个文件。

登录GitHub，在Account Settings，"SSH KEYS"界面，点击Add SSH Key，在key中填入 id_rs.pub 的内容，title随意。然后点击add，这样就能往Git Hub 仓库 push了。

- 关联远程仓库

- >关联

```BASH
git remote add origin  git@github.com:michaelliao/learngit.git
```

- >推送

```BASH
git push -u origin master
```

首次推送的时候加 -u 参数，推送master分支的所有内容。之后再 push 就可以不用 -u。