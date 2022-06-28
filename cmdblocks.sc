data = read_file('data', 'json');

dir_from_list(dir) -> (
    if (dir:0 < 0, return('west'));
    if (dir:0 > 0, return('east'));
    if (dir:1 > 0, return('up'));
    if (dir:1 < 0, return('down'));
    if (dir:2 > 0, return('south'));
    if (dir:2 < 0, return('north'));
    return ('INVALID');
);

for (values(data), 
    loc = _:'position';
    dir = _:'direction';
    for (_:'commands',
        cmd = _;
        cond = 'false';
        cmdblock = 'chain_command_block';
        if (_i == 0, cmdblock = 'command_block');
        if (replace(cmd, '^(?:\\s*)>(?:\\s*)') != cmd, cond = 'true');
        cmd = replace(cmd, '(^\\s*>?\\s*)|(\\s+$)');
        cmd = replace(cmd, '\\s+', ' ');
        set(loc, str('%s[facing=%s,conditional=%s]{"Command":"%s"}', cmdblock, dir_from_list(dir), cond, cmd));
        loc = loc + dir;
    );
);
