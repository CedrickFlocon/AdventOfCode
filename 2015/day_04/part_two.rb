#!/usr/bin/ruby
#Result 1038736

require 'digest'

secret = 'bgvyzdsv'
i = 1

while true do
  md5 = Digest::MD5.new
  md5 << secret.to_s + i.to_s

  if md5.hexdigest =~ /^0{6}/
    puts i
    break
  end

  i +=1

end